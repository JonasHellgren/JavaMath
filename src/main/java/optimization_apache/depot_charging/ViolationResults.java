package optimization_apache.depot_charging;

import lombok.Builder;
import java.util.List;
import java.util.stream.Stream;

@Builder
public record ViolationResults(
        Double depotPowerViolation,
        List<Double> slotPowerViolations,
        List<Double> noChargeWhenSoCIsHighViolations
)

{

    public double barrier(double barrierWeight) {
        return barrierWeight*asList().stream().mapToDouble(v -> Math.pow(v,2)).sum();
    }

    public List<Double> asList() {
        return  Stream.concat(
                        Stream.of(depotPowerViolation),
                        Stream.concat(slotPowerViolations.stream(), noChargeWhenSoCIsHighViolations.stream()))
                .toList();
    }

}
