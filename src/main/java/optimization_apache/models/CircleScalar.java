package optimization_apache.models;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunctionGradient;

import java.util.ArrayList;

public class CircleScalar {
    private ArrayList<Vector2D> points;

    public CircleScalar() {
        points  = new ArrayList<>();
    }

    public void addPoint(double px, double py) {
        points.add(new Vector2D(px, py));
    }

    public double getRadius(Vector2D center) {
        double r = 0;
        for (Vector2D point : points) {
            r += point.distance(center);
        }
        return r / points.size();
    }

    public ObjectiveFunction getObjectiveFunction() {
        return new ObjectiveFunction(params -> {
            Vector2D center = new Vector2D(params[0], params[1]);
            double radius = getRadius(center);
            double sum = 0;
            for (Vector2D point : points) {
                double di = point.distance(center) - radius;
                sum += di * di;
            }
            return sum;
        });
    }

    public ObjectiveFunctionGradient getObjectiveFunctionGradient() {
        return new ObjectiveFunctionGradient(params -> {
            Vector2D center = new Vector2D(params[0], params[1]);
            double radius = getRadius(center);
            // gradient of the sum of squared residuals
            double dJdX = 0;
            double dJdY = 0;
            for (Vector2D pk : points) {
                double dk = pk.distance(center);
                dJdX += (center.getX() - pk.getX()) * (dk - radius) / dk;
                dJdY += (center.getY() - pk.getY()) * (dk - radius) / dk;
            }
            dJdX *= 2;
            dJdY *= 2;

            return new double[] { dJdX, dJdY };
        });
    }
}








