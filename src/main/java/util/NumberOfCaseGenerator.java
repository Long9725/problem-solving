package util;

import java.util.List;

public interface NumberOfCaseGenerator<T> {
    /**
     * r 사이즈까지의 경우의 수 생성
     */
    List<List<T>> generate(List<T> list, int r);

    /**
     * 전체 사이즈 경우의 수 생성
     */
    List<List<T>> generate(List<T> list);

    /**
     * r 사이즈까지의 경우의 수 세기
     */
    long calculate(int n, int r);

    /**
     * 전체 사이즈 경우의 수 생성
     */
    long calculate(int n);
}
