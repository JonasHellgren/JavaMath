package optimization_apache;

import optimization_apache.depot_charging.ViolationResults;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestViolationResults {

    @Test
    public void whenNoViolation_thenZeroBarrier() {
        var vr= ViolationResults.builder()
                .depotPowerViolation(0d)
                .slotPowerViolations(List.of(0d,0d))
                .noChargeWhenSoCIsHighViolations(List.of(0d,0d)).build();
        System.out.println("vr.asList() = " + vr.asList());

        Assert.assertEquals(0,vr.barrier(1, 1),1e-5);
    }

    @Test
    public void whenViolation_thenNonZeroBarrier() {
        var vr= ViolationResults.builder()
                .depotPowerViolation(-1d)
                .slotPowerViolations(List.of(1d,1d))
                .noChargeWhenSoCIsHighViolations(List.of(0d,0d)).build();

        Assert.assertNotEquals(0,vr.barrier(1, 1));
    }

}
