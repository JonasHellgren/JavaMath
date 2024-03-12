package joptimizer;

public class SquaredBracketsModel {

    // Method to calculate the value of the objective function
    public double calculateObjectiveValue(double[] x) {
        double x1 = x[0];
        double x2 = x[1];
        return Math.pow(x1 - 1, 2) + Math.pow(x2 - 2, 2);
    }

    // Method to calculate the gradient of the objective function
    public double[] calculateObjectiveGradient(double[] x) {
        double x1 = x[0];
        double x2 = x[1];
        double gradX1 = 2 * (x1 - 1);
        double gradX2 = 2 * (x2 - 2);
        return new double[]{gradX1, gradX2};
    }

}
