package problem;

import java.util.Arrays;
import java.util.Comparator;

public class PGPracticeInterceptSystem {
    public int solution(int[][] targets) {
        int answer = 0;
        int current = 0;

        Arrays.sort(targets, Comparator.comparingInt(o -> o[1]));

        for (int[] target : targets) {
            final int start = target[0];
            final int end = target[1];
            if(current <= start) {
                current = end;
                answer++;
            }
        }
        return answer;
    }
}
