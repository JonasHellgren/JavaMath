package optimization_apache.models;

import optimization_apache.helpers.FiniteDiffGradient;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunctionGradient;

public class Bowl {

    public ObjectiveFunction getObjectiveFunction() {
        return new ObjectiveFunction(point -> {
            double a = point[0];
            double b = point[1];
            return a * a + b * b;
        });
    }

    public ObjectiveFunctionGradient getGradient() {
        return new ObjectiveFunctionGradient(point -> {
            double a = point[0];
            double b = point[1];
            return new double[]{  2 * a ,  2 * b  }; // Gradient: [2a, 2b]
        });
    }

    public ObjectiveFunctionGradient getFiniteDiffGradient(double eps) {
        FiniteDiffGradient finiteDiffGradient = new FiniteDiffGradient(getObjectiveFunction(), eps);
        return finiteDiffGradient.getFiniteDiffGradient();
    }

}
