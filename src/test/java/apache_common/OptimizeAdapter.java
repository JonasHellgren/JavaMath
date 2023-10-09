package apache_common;

import optimization_apache.helpers.FiniteDiffGradient;
import optimization_apache.models.SumOfThreeModel;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunctionGradient;

public class OptimizeAdapter {

    SumOfThreeModel optModel;
    FiniteDiffGradient gradient;

    public OptimizeAdapter(SumOfThreeModel optModel) {
        this.optModel = optModel;
        this.gradient = new FiniteDiffGradient(getObjectiveFunction(), optModel.eps);
    }

    public ObjectiveFunction getObjectiveFunction() {
        return new ObjectiveFunction(point -> {
            SumOfThreeModel.Variables var = new SumOfThreeModel.Variables();
            var.xList = point;
            return optModel.getObjective(var) + optModel.getPenalty(var);
        });
    }

    public ObjectiveFunctionGradient getGradient() {
        return gradient.getFiniteDiffGradient();
    }

}
