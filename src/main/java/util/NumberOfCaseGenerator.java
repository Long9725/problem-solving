package util;

import java.util.List;

public interface NumberOfCaseGenerator<T> {
    List<List<T>> generate(List<T> list, int r);

    List<List<T>> generate(List<T> list);

    int calculate(int n, int r);

    int calculate(int n);
}
