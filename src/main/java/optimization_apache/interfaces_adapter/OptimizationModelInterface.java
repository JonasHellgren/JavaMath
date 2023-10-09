package optimization_apache.interfaces_adapter;

public interface OptimizationModelInterface {
    double getObjective(double[] xList);
    double getPenalty(double[] xList);
    double getEps();
}
