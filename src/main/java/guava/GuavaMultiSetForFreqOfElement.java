package guava;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;

import java.util.Arrays;
import java.util.Optional;

public class GuavaMultiSetForFreqOfElement {

    public static void main(String[] args) {
        Multiset<Integer> multiset = HashMultiset.create(Arrays.asList(1, 2, 2, 3, 3, 3, 4, 4, 4, 4));
        for (Integer element : multiset.elementSet()) {
            System.out.println(element + ": " + multiset.count(element));
        }
        // Using Guava's Ordering utility to find the most frequent element
        Optional<Multiset.Entry<Integer>> maxEntry = multiset.entrySet().stream()
                .max(Ordering.natural().onResultOf(Multiset.Entry::getCount));

        if (maxEntry.isPresent()) {
            System.out.println("Most frequent number: " + maxEntry.get().getElement() +
                    " (Frequency: " + maxEntry.get().getCount() + ")");
        } else {
            System.out.println("No elements in Multiset.");
        }
    }
}
