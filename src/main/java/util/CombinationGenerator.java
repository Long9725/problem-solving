package util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CombinationGenerator<T> implements NumberOfCaseGenerator<T> {

    @Override
    public List<List<T>> generate(List<T> list, int r) {
        assert (list.size() >= r);
        List<List<T>> combinations = new ArrayList<>();
        backtrack(combinations, new ArrayList<>(), list, 0, r);
        return combinations;
    }

    @Override
    public List<List<T>> generate(List<T> list) {
        return generate(list, list.size());
    }

    @Override
    public long calculate(int n, int r) {
        long[][] memo = new long[n][r];
        return innerCalculate(memo, n, r);
    }

    @Override
    public long calculate(int n) {
        return 1;
    }

    private long innerCalculate(long[][] memo, int n, int r) {
        if (n == r || r == 0) {
            return 1;
        }
        if (memo[n - 1][r - 1] > 0) {
            return memo[n - 1][r - 1];
        }
        memo[n - 1][r - 1] = innerCalculate(memo, n - 1, r - 1) + innerCalculate(memo, n - 1, r);
        return memo[n - 1][r - 1];
    }

    private void backtrack(List<List<T>> combinations, List<T> temp, List<T> list, int start, int remain) {
        if (remain == 0) {
            combinations.add(new ArrayList<>(temp));
            return;
        }

        for (int i = start; i < list.size(); i++) {
            temp.add(list.get(i));
            backtrack(combinations, temp, list, i + 1, remain - 1);
            temp.remove(temp.size() - 1);
        }
    }
}

