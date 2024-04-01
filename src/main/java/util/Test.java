package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        CombinationGenerator<Integer> combinationGenerator = new CombinationGenerator<>();
        PermutationGenerator<Integer> permutationGenerator = new PermutationGenerator<>();
        SubsetGenerator<Integer> subsetGenerator = new SubsetGenerator<>();
        List<Integer> arr = Arrays.asList(1, 2, 3);

        System.out.println(combinationGenerator.generate(arr, 2));
        System.out.println(permutationGenerator.generate(arr, 2));
        System.out.println(subsetGenerator.generate(arr, 2));
    }
}
