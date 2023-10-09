package optimization_apache.models;

import common.ArrayUtil;
import common.ListUtils;
import optimization_apache.helpers.BarrierFunctions;
import optimization_apache.helpers.BoundConstraints;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;

import java.util.ArrayList;
import java.util.List;

public class SumOfThreeModel {
    public static final double A0 = -0, A1 = -1, A2 = -2;
    public static final double LB = 0, UB = 1;
    public static final double SUM_MAX = 1;

    public static class Variables {
        public double[] xList;
    }
    public double eps;

    BarrierFunctions barrier;

    public SumOfThreeModel(double penCoeff, double eps) {
        this.barrier = new BarrierFunctions(penCoeff, "quad");
        this.eps=eps;
    }

    public ObjectiveFunction getObjectiveFunction() {
        return new ObjectiveFunction(point -> {
            Variables var = new Variables();
            var.xList = point;
            return getObjective(var) + getPenalty(var);
        });
    }

     public double getObjective(Variables vars) {
        return A0 * vars.xList[0] + A1 * vars.xList[1] + A2 * vars.xList[2];
    }

    /**
     * the constraint is violated (i.e., ci(vars) > 0)
     */

    public double getPenalty(Variables vars) {
        int nofVars = vars.xList.length;
        double[] lowerBoundsConstrValues = BoundConstraints.getLowerBoundConstraintValues(vars.xList, LB);
        double[] upperBoundsConstrValues = BoundConstraints.getUpperBoundConstraintValues(vars.xList, UB);
        List<Double> penalties = new ArrayList<>();
        for (int i = 0; i < nofVars; i++) {
            penalties.add(barrier.process(lowerBoundsConstrValues[i]));
            penalties.add(barrier.process(upperBoundsConstrValues[i]));
        }
        penalties.add(barrier.process(getSumOfVarsConstraintValue(vars)));
        return ListUtils.sumList(penalties);
    }

    private static double getSumOfVarsConstraintValue(Variables vars) {
        double sum = ArrayUtil.sum(vars.xList);
        return sum - SUM_MAX;
    }

}
