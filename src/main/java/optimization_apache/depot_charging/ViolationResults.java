package optimization_apache.depot_charging;

import lombok.Builder;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
public record ViolationResults(
        Double depotPowerViolation,
        List<Double> slotPowerViolations,
        List<Double> noChargeWhenSoCIsHighViolations,
        Double powerCirculationViolation
)

{

    public double barrier(double barrierWeightLin, double barrierWeightQuad) {
        return asList().stream().mapToDouble(v ->
                barrierWeightLin*Math.abs(v)+ barrierWeightQuad *Math.pow(v,2)).sum();
    }

    public List<Double> asList() {
        return  Stream.of(
                        Stream.of(depotPowerViolation), // Stream of the first single Double
                        slotPowerViolations.stream(), // Stream of the first list
                        noChargeWhenSoCIsHighViolations.stream(), // Stream of the second list
                        Stream.of(powerCirculationViolation) // Stream of the second single Double
                )
                .flatMap(DoubleStream -> DoubleStream) // Flatten the streams into one stream
                .collect(Collectors.toList());
    }

}
