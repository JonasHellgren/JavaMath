package guava;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Doubles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GuavaDoubles {

    public static final String JOINED_STRING = "Joined string: ";

    public static void main(String args[]) {
        GuavaDoubles tester = new GuavaDoubles();
        tester.testDoubles();
    }

    private void testDoubles() {
        double[] doubleArray = {1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0};

        //convert array of primitives to array of objects
        List<Double> objectArray = Doubles.asList(doubleArray);
        System.out.println(objectArray.toString());

        //convert array of objects to array of primitives
        doubleArray = Doubles.toArray(objectArray);
        System.out.println(JOINED_STRING + Doubles.join(", ", doubleArray));

        List<Double> doubleList = List.of(3.5, 2.2, 5.1);
        double[] doubleArrayConv = Doubles.toArray(doubleList);
        String result = Doubles.join(", ", doubleArrayConv);
        System.out.println(JOINED_STRING + result);

        //check if element is present in the list of primitives or not
        System.out.println("5.0 is in list? " + Doubles.contains(doubleArray, 5.0f));

        //return the index of element
        System.out.println("5.0 position in list " + Doubles.indexOf(doubleArray, 5.0f));

        //Returns the minimum
        System.out.println("Min: " + Doubles.min(doubleArray));

        List<Double> numbers = Arrays.asList(5d, 3d, 9d, 1d, 4d);
        double min = Ordering.natural().min(numbers);
        System.out.println("min = " + min);

        List<Double> doubles = Arrays.asList(3.7, 4.1, 2.5, 10.2, 1.9);
        double minColl = Collections.min(doubles);
        System.out.println("Minimum value: " + minColl);

        //Returns the maximum
        System.out.println("Max: " + Doubles.max(doubleArray));

        System.out.println(JOINED_STRING + Doubles.join(", ", doubleArray));

        Doubles.sortDescending(doubleArray);
        System.out.println("Sorted array: " + Arrays.toString(doubleArray));

        int result1 = Doubles.compare(1, 2);
        int result2 = Doubles.compare(2, 0);
        System.out.println("Comparison result (array1 vs array2): " + result1); // 0, arrays are equal
        System.out.println("Comparison result (array1 vs array3): " + result2);

        double[] array1 = {1.0, 2.0};
        double[] array2 = {3.0, 4.0};
        double[] concatenated = Doubles.concat(array1, array2);
        System.out.println(JOINED_STRING + Doubles.join(", ", concatenated));

        double[] doubleArr = {7.5, 7.5, 9.0, 10.0, 7.5};
        boolean cont = Doubles.contains(doubleArray, 7.5);

        System.out.println("7.5 in contained " + cont);

        double constrainedValue = Doubles.constrainToRange(10, 5, 9);  //as clip

        System.out.println("Original: " + 10);
        System.out.println("Constrained: " + constrainedValue);

        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        List<Integer> reversed = Lists.reverse(list);
        System.out.println("Reversed list: " + reversed);

        int lastIndex = Doubles.lastIndexOf(doubleArr, 9.0);
        System.out.println("Last index of 2: " + lastIndex);

        double value1 = 1.0 / 0.0;  // This will be Infinity
        double value2 = Math.sqrt(-1);  // This will be NaN
        double value3 = 123.456;  // This is a normal finite value

        System.out.println("Is value1 finite? " + Doubles.isFinite(value1));
        System.out.println("Is value2 finite? " + Doubles.isFinite(value2));
        System.out.println("Is value3 finite? " + Doubles.isFinite(value3));

    }
}