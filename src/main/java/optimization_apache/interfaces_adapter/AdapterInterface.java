package optimization_apache.interfaces_adapter;

import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunctionGradient;

public interface AdapterInterface {

    ObjectiveFunction getObjectiveFunction();
    ObjectiveFunctionGradient getGradient();

}
