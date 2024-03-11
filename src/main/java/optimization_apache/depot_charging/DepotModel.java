package optimization_apache.depot_charging;

import com.google.common.base.Preconditions;
import lombok.Builder;
import optimization_apache.helpers.FiniteDiffGradientCalculator;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;

import java.util.Arrays;
import java.util.stream.IntStream;

import static common.ListUtils.arrayPrimitiveDoublesToList;

public class DepotModel {

    public static final double EPS = 1e-5;
    public static final double BARRIER_WEIGHT = 1e-1;
    double[] kList;
    double[] pMaxList;
    double[] socList;
    double pDepotMax;
    double socMax;

    final FiniteDiffGradientCalculator finiteDiffGradient;

    @Builder
    public DepotModel(double[] kList, double[] pMaxList, double[] socList, double pDepotMax, double socMax) {
        Preconditions.checkArgument(kList.length==pMaxList.length && kList.length==socList.length, "Non equal inputs");

        this.kList = kList;
        this.pMaxList = pMaxList;
        this.socList = socList;
        this.pDepotMax = pDepotMax;
        this.socMax = socMax;
        this.finiteDiffGradient=new FiniteDiffGradientCalculator(getObjectiveFunction(), EPS);
    }

    public ObjectiveFunction getObjectiveFunction() {
        return new ObjectiveFunction(point -> {
            return getObjective(point) + getPenalty(point);
        });
    }

    private  double getObjective(double[] xList) {
        double[] resList=new double[xList.length];
        IntStream.range(0, xList.length)
                .forEach(i -> resList[i] = kList[i] * xList[i]);
        return Arrays.stream(resList).sum();
    }

    /**
     * the constraint is violated (i.e., ci(vars) > 0)
     */

    private double getPenalty(double[] xList) {
        var violRest=ViolationResults.builder()
                .depotPowerViolation(getDepotPowerViolations(xList))
                .slotPowerViolations(arrayPrimitiveDoublesToList(getSlotPowerViolations(xList)))
                .noChargeWhenSoCIsHighViolations(arrayPrimitiveDoublesToList((getNoPowerIsHighSocViolations(xList))))
                .build();

        return violRest.barrier(BARRIER_WEIGHT);
    }

    private double getDepotPowerViolations(double[] xList) {
        return Math.max(0, Arrays.stream(xList).sum() - pDepotMax);
    }

    private double[] getNoPowerIsHighSocViolations(double[] xList) {
        double[] noChargeWhenPresentSoCAboveTargetArr=new double[xList.length];
        IntStream.range(0, xList.length)
                .forEach(i ->  noChargeWhenPresentSoCAboveTargetArr[i]=
                        Math.max(0,socList[i]>socMax ? Math.abs(xList[i])-1e-2:0d));
        return noChargeWhenPresentSoCAboveTargetArr;
    }

    private double[] getSlotPowerViolations(double[] xList) {
        double[] slotPowerArr=new double[xList.length];
        IntStream.range(0, xList.length)
                .forEach(i -> slotPowerArr[i]= Math.max(0,xList[i]-pMaxList[i]));
        return slotPowerArr;
    }

}
