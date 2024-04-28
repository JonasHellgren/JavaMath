package guava;

import com.google.common.collect.*;

import java.util.Optional;
import java.util.Set;

public class GuavaMultiSet {

    public static void main(String[] args) {
        Multiset<Integer> multiset = HashMultiset.create();
        multiset.add(1, 5); // Adds the element 1 five times
        multiset.add(2);
        multiset.remove(1, 3); // Removes three occurrences of 1
        multiset.setCount(1, 2); // Set the count directly, overriding the current count

        int count1 = multiset.count(1); // Returns the number of occurrences of 1
        Set<Integer> elementSet = multiset.elementSet(); // Gets the unique elements
        for (Multiset.Entry<Integer> entry : multiset.entrySet()) {
            System.out.println(entry.getElement() + " - " + entry.getCount());
        }


        int totalSize = multiset.size(); // Total elements, including duplicates
        int uniqueSize = multiset.elementSet().size(); // Just the unique elements

        System.out.println("count1 = " + count1);
        System.out.println("elementSet = " + elementSet);
        System.out.println("totalSize = " + totalSize);
        System.out.println("uniqueSize = " + uniqueSize);

        ImmutableMultiset<Integer> highestCountFirst = Multisets.copyHighestCountFirst(multiset);
        System.out.println("highestCountFirst = " + highestCountFirst);
        System.out.println("highest count = " + highestCountFirst.stream().findFirst().orElseThrow());

        Ordering<Multiset.Entry<Integer>> byCountOrdering = Ordering.natural().onResultOf(Multiset.Entry::getCount);
        Optional<Multiset.Entry<Integer>> maxEntry = multiset.entrySet().stream()
                .max(byCountOrdering);
        System.out.println("element highest count = " + maxEntry.orElseThrow().getElement());


    }
}
