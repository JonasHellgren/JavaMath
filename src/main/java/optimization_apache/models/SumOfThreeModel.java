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
     public double getObjective(double[] point) {
        return A0 * point[0] + A1 * point[1] + A2 * point[2];
    }

    /**
     * the constraint is violated (i.e., ci(vars) > 0)
     */

    @Override
    public double getPenalty(double[] point) {
        int nofVars = point.length;
        double[] lowerBoundsConstrValues = BoundConstraints.getLowerBoundConstraintValues(point, LB);
        double[] upperBoundsConstrValues = BoundConstraints.getUpperBoundConstraintValues(point, UB);
        List<Double> penalties = new ArrayList<>();
        for (int i = 0; i < nofVars; i++) {
            penalties.add(barrier.process(lowerBoundsConstrValues[i]));
            penalties.add(barrier.process(upperBoundsConstrValues[i]));
        }
        penalties.add(barrier.process(getSumOfVarsConstraintValue(point)));
        return ListUtils.sumList(penalties);
    }

    private static double getSumOfVarsConstraintValue(double[] point) {
        double sum = ArrayUtil.sum(point);
        return sum - SUM_MAX;
    }

}
