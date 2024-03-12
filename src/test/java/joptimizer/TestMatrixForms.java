package joptimizer;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.functions.LinearMultivariateRealFunction;
import com.joptimizer.functions.PDQuadraticMultivariateRealFunction;
import com.joptimizer.optimizers.JOptimizer;
import com.joptimizer.optimizers.OptimizationRequest;
import com.joptimizer.optimizers.OptimizationResponse;
import lombok.extern.java.Log;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Log
public class TestMatrixForms {

    public static final double TOL = 1e-5;

    /**
     * Linear objective, linear constrained.
     * It simulates the type of optimization occurring in feasibility searching
     * in a problem with cost
     * cost=x0+x1
     * and constraints:
     * -x0-x1 < 0  (x0+x1>0)
     *  x0-x1-1 < 0  (x0-x1<1)
     */
    @Test
    public void testLinearProgramming() throws Exception {
        log.info("testLinearProgramming");

        double[] c0 = new double[] { 0, 1 };
        var objectiveFunction = new LinearMultivariateRealFunction(c0, 0);

        //inequalities
        var inequalities = new ConvexMultivariateRealFunction[2];
        double[] c1 = new double[] { -1, -1 };
        inequalities[0] = new LinearMultivariateRealFunction(c1, 0);
        double[] c2 = new double[] { 1, -1 };
        inequalities[1] = new LinearMultivariateRealFunction(c2, -1);

        var or = new OptimizationRequest();
        or.setF0(objectiveFunction);
        or.setInitialPoint(new double[] { 1.4, 0.5});
        or.setFi(inequalities);
        or.setMu(100d);

        var opt = new JOptimizer();
        opt.setOptimizationRequest(or);
        opt.optimize();

        OptimizationResponse response = opt.getOptimizationResponse();
        double[] sol = response.getSolution();
        log.info("sol   : " + ArrayUtils.toString(sol));
        log.info("value : " + objectiveFunction.value(new DenseDoubleMatrix1D(sol)));
        assertEquals( 0.5, sol[0], TOL);
        Assertions.assertEquals(-0.5, sol[1], TOL);
    }


    /**
     * Test QP in 3-dim
     * Min( 1/2 * xT.x) s.t.
     * 	x1 <= -10
     * This problem can't be solved without an initial point,
     * because the relative PhaseI problem is undetermined.
     * Submitted 01/06/2012 by Klaas De Craemer
     */
    @Test
    public void testQP() throws Exception {
        log.info("testQP");

        // Objective function
        double[][] pMatrix = new double[][] {
                { 1, 0, 0 },
                { 0, 1, 0 },
                { 0, 0, 1 }};
        var objectiveFunction = new PDQuadraticMultivariateRealFunction(pMatrix, null, 0);

        //inequalities
        var inequalities = new ConvexMultivariateRealFunction[1];
        inequalities[0] = new LinearMultivariateRealFunction(new double[]{1, 0, 0}, 10);// x1 <= -10

        //optimization problem
        var or = new OptimizationRequest();
        or.setF0(objectiveFunction);
        or.setInitialPoint(new double[]{-11, 1, 1});
        or.setFi(inequalities);
//        or.setToleranceFeas(1.E-12);
//        or.setTolerance(1.E-12);

        //optimization
        var opt = new JOptimizer();
        opt.setOptimizationRequest(or);
        opt.optimize();


        var response = opt.getOptimizationResponse();
        double[] sol = response.getSolution();
        double value = objectiveFunction.value(new DenseDoubleMatrix1D(sol));
        log.info("sol   : " + ArrayUtils.toString(sol));
        log.info("value : " + value);
        assertEquals(-10., sol[0], 1.E-6);
        assertEquals(  0., sol[1], 1.E-6);
        assertEquals(  0., sol[2], 1.E-6);
        assertEquals( 50.,  value, 1.E-6);
    }

    /**
     * Test QP in 3-dim
     * Min(1/2*xQx'+q'x) s.t.
     * 	x1 <= -10
     * This problem can't be solved without an initial point,
     * because the relative PhaseI problem is undetermined.
     * Submitted 01/06/2012 by Klaas De Craemer
     */
    @Test
    public void testQPWithQvector() throws Exception {
        log.info("testQP");

        // Objective function
        double[][] pMatrix = new double[][] {
                { 1e-1, 0, 0 },
                { 0, 1e-1, 0 },
                { 0, 0, 1e-1}};
        double[] qVector= new double[]{-1, -2, -3};
        var objectiveFunction = new PDQuadraticMultivariateRealFunction(pMatrix, qVector, 0);

        //inequalities
        var inequalities = new ConvexMultivariateRealFunction[3];
        inequalities[0] = new LinearMultivariateRealFunction(new double[]{1, 0, 0}, -10);// x1 <= 10
        inequalities[1] = new LinearMultivariateRealFunction(new double[]{0, 1, 0}, -10);// x2 <= 10
        inequalities[2] = new LinearMultivariateRealFunction(new double[]{0, 0, 1}, -7);// x3 <= 7


        //optimization problem
        var or = new OptimizationRequest();
        or.setF0(objectiveFunction);
        or.setInitialPoint(new double[]{1, 1, 1});
        or.setFi(inequalities);
//        or.setToleranceFeas(1.E-12);
//        or.setTolerance(1.E-12);

        //optimization
        var opt = new JOptimizer();
        opt.setOptimizationRequest(or);
        opt.optimize();


        var response = opt.getOptimizationResponse();
        double[] sol = response.getSolution();
        double value = objectiveFunction.value(new DenseDoubleMatrix1D(sol));
        log.info("sol   : " + ArrayUtils.toString(sol));
        log.info("value : " + value);
        assertEquals(-10., sol[0], 1.E-6);
        assertEquals(  0., sol[1], 1.E-6);
        assertEquals(  0., sol[2], 1.E-6);
        assertEquals( 50.,  value, 1.E-6);
    }


}
