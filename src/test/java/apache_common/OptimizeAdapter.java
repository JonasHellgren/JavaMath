package apache_common;

import optimization_apache.helpers.FiniteDiffGradientFactory;
import optimization_apache.models.SumOfThree;
import optimization_apache.models.SumOfThreeModel;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunctionGradient;

public class OptimizeAdapter {

    SumOfThreeModel optModel;
    FiniteDiffGradientFactory gradientFactory;

    public OptimizeAdapter(SumOfThreeModel optModel) {
        this.optModel = optModel;
        this.gradientFactory = new FiniteDiffGradientFactory(getObjectiveFunction(), optModel.eps);
    }

    public ObjectiveFunction getObjectiveFunction() {
        return new ObjectiveFunction(point -> {
            SumOfThreeModel.Variables var = new SumOfThreeModel.Variables();
            var.xList = point;
            return optModel.getObjective(var) + optModel.getPenalty(var);
        });
    }

    public ObjectiveFunctionGradient getGradientFactory() {
        return gradientFactory.getFiniteDiffGradient();
    }

}
