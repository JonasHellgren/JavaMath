package guava;

import com.google.common.collect.Ordering;
import org.apache.commons.math3.util.Pair;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortListOfPairs {
    public static void main(String[] args) {
        List<Pair<Integer, Double>> pairs = Arrays.asList(
                new Pair<>(1, 9.0),
                new Pair<>(2, 4.5),
                new Pair<>(3, 7.2)
        );

        // Custom Ordering using the second element of the Pair (Double value)
        Ordering<Pair<Integer, Double>> orderByDouble = Ordering.from(Comparator.comparingDouble(Pair::getValue));

        // Sort the list
        List<Pair<Integer, Double>> sortedPairs = orderByDouble.sortedCopy(pairs);

        // Print the sorted list
        sortedPairs.forEach(pair -> System.out.println(pair.getKey() + ": " + pair.getValue()));
    }
}