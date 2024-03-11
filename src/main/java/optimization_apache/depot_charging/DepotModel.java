package optimization_apache.depot_charging;

import com.google.common.base.Preconditions;
import common.Conditionals;
import common.DefaultIf;
import common.ListUtils;
import common.MathUtils;
import lombok.Builder;
import lombok.Setter;
import optimization_apache.helpers.FiniteDiffGradientCalculator;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunctionGradient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static common.Conditionals.executeOneOfTwo;
import static common.ListUtils.arrayPrimitiveDoublesToList;
import static common.MathUtils.normalize;

public class DepotModel {

    public static final double EPS = 1e-5;
    public static final double BARRIER_WEIGHT = 1e-1;
    public static final double SMALL_POWER = 1e-2;
    public static final double POWER_MIN = 1e-2;
    double[] kList;
    double[] pMaxList;
    double[] socList;
    double pDepotMax;
    double socMax;
    @Setter double barrierWeightLin;
    @Setter double barrierWeightQuad;

    final FiniteDiffGradientCalculator finiteDiffGradient;

    @Builder
    public DepotModel(double[] kList, double[] pMaxList, double[] socList, double pDepotMax, double socMax,
                      Double barrierWeight, Double barrierWeightQuad) {
        Preconditions.checkArgument(kList.length==pMaxList.length && kList.length==socList.length, "Non equal inputs");

        this.kList = kList;
        this.pMaxList = pMaxList;
        this.socList = socList;
        this.pDepotMax = pDepotMax;
        this.socMax = socMax;
        this.barrierWeightLin = DefaultIf.defaultIfNullDouble.apply(barrierWeight,BARRIER_WEIGHT);
        this.barrierWeightQuad= DefaultIf.defaultIfNullDouble.apply(barrierWeightQuad,BARRIER_WEIGHT);
        this.finiteDiffGradient=new FiniteDiffGradientCalculator(getObjectiveFunction(), EPS);
    }

    public ObjectiveFunction getObjectiveFunction() {
        return new ObjectiveFunction(point -> getObjective(point) + violations(point).barrier(barrierWeightLin,barrierWeightQuad));
    }

    private  double getObjective(double[] xList) {
        double[] resList=new double[xList.length];
        IntStream.range(0, xList.length)
                .forEach(i -> resList[i] = -kList[i] * xList[i]);
        return Arrays.stream(resList).sum();
    }

    /**
     * the constraint is violated (i.e., ci(vars) > 0)
     */

    public ViolationResults violations(double[] xList) {

        return ViolationResults.builder()
                .depotPowerViolation(getDepotPowerViolations(xList))
                .slotPowerViolations(arrayPrimitiveDoublesToList(getSlotPowerViolations(xList)))
                .noChargeWhenSoCIsHighViolations(arrayPrimitiveDoublesToList((getNoPowerIsHighSocViolations(xList))))
                .powerCirculationViolation(powerCirculationViolation(xList))
                .build();
    }

    public ObjectiveFunctionGradient getFiniteDiffGradient() {
        return finiteDiffGradient.getFiniteDiffGradient();
    }

    private double getDepotPowerViolations(double[] xList) {
        return Math.max(0, Arrays.stream(xList).sum() - pDepotMax);
    }

    private double[] getNoPowerIsHighSocViolations(double[] xList) {
        double[] noChargeWhenPresentSoCAboveTargetArr=new double[xList.length];
        IntStream.range(0, xList.length)
                .forEach(i ->  noChargeWhenPresentSoCAboveTargetArr[i]=
                        Math.max(0,socList[i]>socMax ? Math.abs(xList[i])- SMALL_POWER :0d));
        return noChargeWhenPresentSoCAboveTargetArr;
    }

    private double[] getSlotPowerViolations(double[] xList) {
        double[] slotPowerArr=new double[xList.length];
        IntStream.range(0, xList.length)
                .forEach(i -> slotPowerArr[i]= Math.max(0,xList[i]-pMaxList[i]));
        return slotPowerArr;
    }

    /***
     *  powerList=[1 2 3 4 2] -> powerPosSum=12, powerNegSum=0; ->  powerCirculation=0;
     *  powerList=[1 -2 3 4 2] -> powerPosSum=10, powerNegSum=2; ->  powerCirculation=2;
     *  powerList=[1 -2 0 0 0] -> powerPosSum=1, powerNegSum=2; ->  powerCirculation=1
     *  powerList=[1 -1 0 0 0] -> powerPosSum=1, powerNegSum=1; ->  powerCirculation=1
     */

    private double powerCirculationViolation(double[] xList) {
        List<Double> powerPosList = new ArrayList<>();
        List<Double> powerNegList = new ArrayList<>();
        ListUtils.arrayPrimitiveDoublesToList(xList).forEach(power -> executeOneOfTwo(MathUtils.isPos(power),
                () -> powerPosList.add(Math.abs(power)),
                () -> powerNegList.add(Math.abs(power))));

        double powerPosSum = ListUtils.sumList(powerPosList);
        double powerNegSum = ListUtils.sumList(powerNegList);
        double powerCirculation = Math.min(powerPosSum, powerNegSum);
        return Math.max(0,powerCirculation- POWER_MIN);
    }




}
