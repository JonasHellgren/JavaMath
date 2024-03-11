package optimization_apache;

import optimization_apache.depot_charging.DepotModel;
import optimization_apache.depot_charging.ViolationResults;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestDepotModel {

    public static final int BARRIER_WEIGHT = 1;
    public static final double TOL = 1e-4;
    DepotModel model;

    @Before
    public void init() {
        model= DepotModel.builder()
                .kList(new double[]{1,1,1})
                .pMaxList(new double[]{1,1,1})
                .socList(new double[]{1,1,1})
                .pDepotMax(1)
                .socMax(1)
                .build();
    }

    @Test
    public void whenXAllZero_thenFeasible() {
        double pen=model.violations(new double[]{0,0,0}).barrier(BARRIER_WEIGHT, 1);
        System.out.println("pen = " + pen);
        Assert.assertEquals(0,pen, TOL);
    }

    @Test
    public void whenXAllOne_thenNotFeasible() {
        ViolationResults violations = model.violations(new double[]{1, 1, 1});
        double pen= violations.barrier(BARRIER_WEIGHT, 1);
        System.out.println("violations = " + violations);
        Assert.assertNotEquals(0,pen, TOL);
    }

    @Test
    public void largerXIsBetter() {
        double cost0 = model.getObjectiveFunction().getObjectiveFunction().value(new double[]{1, 1, 1});
        double cost1 = model.getObjectiveFunction().getObjectiveFunction().value(new double[]{0.1, 0.1, 0.1});

        Assert.assertTrue(cost0>cost1);
    }




}
