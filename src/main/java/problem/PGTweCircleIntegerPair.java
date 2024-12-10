package problem;

public class PGTweCircleIntegerPair {
    public long solution(int r1, int r2) {
        // r2 원 위의 점
        long answer = 4;
        // 사각형 변이 2일때 -> 8
        // 사각형 변이 4일때 -> 16
        // 사각형 변이 6일때 -> 24
        // 사각형 변이 2 x n일때 -> 8 x n

        int n = r2 - r1;

        for (int i = r1; i < r1 + n; i++) {
            answer += 8L * i;
        }
        return answer;
    }
}
