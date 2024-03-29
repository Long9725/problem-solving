package util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PermutationGenerator<T> implements NumberOfCaseGenerator<T> {
    @Override
    public List<List<T>> generate(List<T> list, int r) {
        assert (list.size() >= r);
        List<List<T>> permutations = new ArrayList<>();
        boolean[] visited = new boolean[list.size()];
        backtrack(permutations, list, new ArrayList<>(), visited, r);
        return permutations;
    }

    @Override
    public List<List<T>> generate(List<T> list) {
        return generate(list, list.size());
    }

    public List<List<T>> generateDuplicate(List<T> list, int r) {
        assert (list.size() >= r);
        List<List<T>> permutations = new ArrayList<>();
        backtrack(permutations, list, new ArrayList<>(), r);
        return permutations;
    }

    public List<List<T>> generateDuplicate(List<T> list) {
        return generateDuplicate(list, list.size());
    }


    @Override
    public long calculate(int n, int r) {
        long[] memoFraction = new long[n];
        long[] memoMolecule = new long[n - r];
        return factorial(memoFraction, n) / factorial(memoMolecule, n - r);
    }

    @Override
    public long calculate(int n) {
        return 1;
    }

    private void backtrack(List<List<T>> permutations, List<T> list, List<T> temp, boolean[] visited, int remain) {
        if (remain == 0) {
            permutations.add(new ArrayList<>(temp));
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            if (!visited[i]) {
                visited[i] = true;
                temp.add(list.get(i));
                backtrack(permutations, list, temp, visited, remain - 1);
                temp.remove(temp.size() - 1);
                visited[i] = false; // 백트래킹
            }
        }
    }

    private void backtrack(List<List<T>> permutations, List<T> list, List<T> temp, int remain) {
        if (remain == 0) {
            permutations.add(new ArrayList<>(temp));
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            temp.add(list.get(i));
            backtrack(permutations, list, temp, remain - 1);
            temp.remove(temp.size() - 1);
        }
    }


    private long factorial(long[] memo, int n) {
        if (n <= 1) return 1;
        if (memo[n-1] != 0) return memo[n-1];

        memo[n-1] = n * factorial(memo, n - 1);
        return memo[n-1];
    }
}
