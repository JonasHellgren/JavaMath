package optimization_apache;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.jet.math.Mult;
import com.joptimizer.functions.*;
import com.joptimizer.optimizers.BarrierMethod;
import com.joptimizer.optimizers.OptimizationRequest;
import com.joptimizer.optimizers.OptimizationResponse;
import junit.framework.TestCase;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestBarrierJOpt extends TestCase {


        private Algebra ALG = Algebra.DEFAULT;
        private DoubleFactory1D F1 = DoubleFactory1D.dense;
        private DoubleFactory2D F2 = DoubleFactory2D.dense;
        private Log log = LogFactory.getLog(this.getClass().getName());

        /**
         * Quadratic objective with linear eq and ineq.
         */
        public void testOptimize() throws Exception {
            log.debug("testOptimize");
            DoubleMatrix2D pMatrix = F2.make(new double[][] {
                    { 1.68, 0.34, 0.38 },
                    { 0.34, 3.09, -1.59 },
                    { 0.38, -1.59, 1.54 } });
            DoubleMatrix1D qVector = F1.make(new double[] { 0.018, 0.025, 0.01 });

            // Objective function
            double theta = 0.01522;
            DoubleMatrix2D P = pMatrix.assign(Mult.mult(theta));
            DoubleMatrix1D q = qVector.assign(Mult.mult(-1));
            PDQuadraticMultivariateRealFunction objectiveFunction = new PDQuadraticMultivariateRealFunction(P.toArray(), q.toArray(), 0);

            ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[3];
            inequalities[0] = new LinearMultivariateRealFunction(new double[] {-1, 0, 0 }, 0);
            inequalities[1] = new LinearMultivariateRealFunction(new double[] { 0, -1, 0 }, 0);
            inequalities[2] = new LinearMultivariateRealFunction(new double[] {	0, 0, -1 }, 0);

            OptimizationRequest or = new OptimizationRequest();
            or.setCheckKKTSolutionAccuracy(true);
            or.setCheckProgressConditions(true);
            or.setF0(objectiveFunction);
            or.setInitialPoint(new double[] { 0.6, 0.2, 0.2 });
            // equalities
            or.setA(new double[][] { { 1, 1, 1 } });
            or.setB(new double[] { 1 });
            //tolerances
            or.setTolerance(1.E-5);

            // optimization
            BarrierFunction bf = new LogarithmicBarrier(inequalities, 3);
            BarrierMethod opt = new BarrierMethod(bf);
            opt.setOptimizationRequest(or);

            opt.optimize();

            /*
            if(returnCode==OptimizationResponse.FAILED){
                fail();
            }*/

            OptimizationResponse response = opt.getOptimizationResponse();
            double[] expectedSol = {0.04632311555988555, 0.5086308460954377, 0.44504603834467693};
          //  double expectedValue = objectiveFunction.value(expectedSol);
            double[] sol = response.getSolution();
           // double value = objectiveFunction.value(sol);
            log.debug("sol   : " + ArrayUtils.toString(sol));
         //   log.debug("value : " + value);
           // assertEquals(expectedValue, value, or.getTolerance());*/
        }

        /**
         * Quadratic objective with linear eq and ineq
         * with not-feasible initial point.
         */
        public void testOptimize2() throws Exception {
            log.debug("testOptimize2");
            DoubleMatrix2D pMatrix = F2.make(new double[][] {
                    { 1.68, 0.34, 0.38 },
                    { 0.34, 3.09, -1.59 },
                    { 0.38, -1.59, 1.54 } });
            DoubleMatrix1D qVector = F1.make(new double[] { 0.018, 0.025, 0.01 });

            // Objective function.
            double theta = 0.01522;
            DoubleMatrix2D P = pMatrix.assign(Mult.mult(theta));
            DoubleMatrix1D q = qVector.assign(Mult.mult(-1));
            PDQuadraticMultivariateRealFunction objectiveFunction = new PDQuadraticMultivariateRealFunction(P.toArray(), q.toArray(), 0);

            ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[3];
            inequalities[0] = new LinearMultivariateRealFunction(new double[] {-1, 0, 0 }, 0);
            inequalities[1] = new LinearMultivariateRealFunction(new double[] { 0, -1, 0 }, 0);
            inequalities[2] = new LinearMultivariateRealFunction(new double[] {	0, 0, -1 }, 0);

            OptimizationRequest or = new OptimizationRequest();
            or.setNotFeasibleInitialPoint(new double[] { -0.2, 1.0, 0.2 });
            or.setCheckKKTSolutionAccuracy(true);
            or.setF0(objectiveFunction);
            // equalities
            or.setA(new double[][] { { 1, 1, 1 } });
            or.setB(new double[] { 1 });
            //tolerances
            or.setTolerance(1.E-5);

            // optimization
            BarrierFunction bf = new LogarithmicBarrier(inequalities, 3);
            BarrierMethod opt = new BarrierMethod(bf);
            opt.setOptimizationRequest(or);
         /*   int returnCode = opt.optimize();

            if(returnCode==OptimizationResponse.FAILED){
                fail();
            }
            */

            OptimizationResponse response = opt.getOptimizationResponse();
            double[] expectedSol = {0.04632311555988555, 0.5086308460954377, 0.44504603834467693};
          //  double expectedValue = objectiveFunction.value(expectedSol);
            double[] sol = response.getSolution();
           // double value = objectiveFunction.value(sol);
            log.debug("sol   : " + ArrayUtils.toString(sol));
         //   log.debug("value : " + value);
         //   assertEquals(expectedValue, value, or.getTolerance());*/
        }

}
