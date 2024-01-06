package util;

import java.util.ArrayList;
import java.util.List;

public class SubsetGenerator<T> implements NumberOfCaseGenerator<T> {
    /**
     * r 사이즈까지의 부분집합 생성
     */
    @Override
    public List<List<T>> generate(List<T> list, int r) {
        List<List<T>> subsets = new ArrayList<>();
        backtrackSubsets(subsets, list, new ArrayList<>(), 0, r);
        return subsets;
    }

    /**
     * 자기 자신을 포함하는 부분집합 생성
     */
    @Override
    public List<List<T>> generate(List<T> list) {
        return generate(list, list.size());
    }

    /**
     * 부분집합 개수 - 2^(n-r)
     */
    @Override
    public long calculate(int n, int r) {
        return (long) Math.floor(Math.pow(2, n-r));
    }

    /**
     * 부분집합 개수 - 2^n
     */
    @Override
    public long calculate(int n) {
        return calculate(n, 0);
    }

    private void backtrackSubsets(List<List<T>> subsets, List<T> list, List<T> temp, int start, int max) {
        if (temp.size() <= max) {
            subsets.add(new ArrayList<>(temp));
        }

        for (int i = start; i < list.size(); i++) {
            temp.add(list.get(i));
            backtrackSubsets(subsets, list, temp, i + 1, max);
            temp.remove(temp.size() - 1);
        }
    }
}
