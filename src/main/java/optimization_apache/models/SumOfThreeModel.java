package optimization_apache.models;

import common.ArrayUtil;
import common.ListUtils;
import optimization_apache.helpers.BarrierFunctions;
import optimization_apache.helpers.BoundConstraints;
import optimization_apache.interfaces_adapter.OptimizationModelInterface;

import java.util.ArrayList;
import java.util.List;

public class SumOfThreeModel implements OptimizationModelInterface {
    public static final double A0 = -0, A1 = -1, A2 = -2;
    public static final double LB = 0, UB = 1;
    public static final double SUM_MAX = 1;

    BarrierFunctions barrier;
    double eps;

    public SumOfThreeModel(double penCoeff, double eps) {
        this.barrier = new BarrierFunctions(penCoeff, "quad");
        this.eps=eps;
    }

    @Override
    public double getEps() {
        return eps;
    }


    @Override
     public double getObjective(double[] xList) {
        return A0 * xList[0] + A1 * xList[1] + A2 * xList[2];
    }

    /**
     * the constraint is violated (i.e., ci(vars) > 0)
     */

    @Override
    public double getPenalty(double[] xList) {
        int nofVars = xList.length;
        double[] lowerBoundsConstrValues = BoundConstraints.getLowerBoundConstraintValues(xList, LB);
        double[] upperBoundsConstrValues = BoundConstraints.getUpperBoundConstraintValues(xList, UB);
        List<Double> penalties = new ArrayList<>();
        for (int i = 0; i < nofVars; i++) {
            penalties.add(barrier.process(lowerBoundsConstrValues[i]));
            penalties.add(barrier.process(upperBoundsConstrValues[i]));
        }
        penalties.add(barrier.process(getSumOfVarsConstraintValue(xList)));
        return ListUtils.sumList(penalties);
    }

    private static double getSumOfVarsConstraintValue(double[] xList) {
        double sum = ArrayUtil.sum(xList);
        return sum - SUM_MAX;
    }

}
