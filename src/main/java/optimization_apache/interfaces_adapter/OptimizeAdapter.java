package optimization_apache.interfaces_adapter;

import optimization_apache.helpers.FiniteDiffGradientCalculator;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunctionGradient;

public class OptimizeAdapter implements AdapterInterface {

    OptimizationModelInterface optModel;
    FiniteDiffGradientCalculator gradient;

    public OptimizeAdapter(OptimizationModelInterface optModel) {
        this.optModel = optModel;
        this.gradient = new FiniteDiffGradientCalculator(getObjectiveFunction(), optModel.getEps());
    }

    @Override
    public ObjectiveFunction getObjectiveFunction() {
        return new ObjectiveFunction(point ->
                optModel.getObjective(point) + optModel.getPenalty(point));
    }

    @Override
    public ObjectiveFunctionGradient getGradient() {
        return gradient.getFiniteDiffGradient();
    }

}
