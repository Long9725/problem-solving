package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Soccer {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        SoccerSolve solve = new SoccerSolve();
        int percentageGainScoreA;
        int percentageGainScoreB;

        percentageGainScoreA = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(bf.readLine());
        percentageGainScoreB = Integer.parseInt(st.nextToken());

        System.out.println(solve.solve(percentageGainScoreA, percentageGainScoreB));
    }
}

class SoccerSolve {
    public double solve(int percentageGainScoreA, int percentageGainScoreB) {
        /**
         * 90분 경기를 5분 단위로 나누면 총 18번
         * 적어도 한 팀이 골을 소수로 득점할 확률 => 소수가 2, 3, 5, 7, 11, 13, 17 이 끝
         * A팀, B팀, A,B팀 => 둘 다 소수로 득점 못 할 확률
         * 승리 확률 => per / 패배 확률 1 - per
         * 각 골 별로 확률 계산해서 전부 더하기 => 1 - (전체 더한 확률)
         *
         * 근데 A가 골 넣었을 때 B가 넣을 확률 / 넣지 않을 확률 & 반대도 해야되잖아?
         * 한 쪽의 개별 확률을 구한 다음에, B의 개별 확률?
         * 이항분포 -> nCr * p^r * (1-p) ^ (n-r)
         * */
        double answer = 0;
        int MAX_SCORE = 18;
        int[] notPrimeNumbers = {0, 1, 4, 6, 8, 9, 10, 12, 14, 15, 16, 18};
        double[] scorePercentagesA = new double[notPrimeNumbers.length];
        double[] scorePercentagesB = new double[notPrimeNumbers.length];

        for (int i = 0; i < notPrimeNumbers.length; i++) {
            int gainScore = notPrimeNumbers[i];

            scorePercentagesA[i] = calculatePercentage((double) percentageGainScoreA / 100, MAX_SCORE, gainScore);
            scorePercentagesB[i] = calculatePercentage((double) percentageGainScoreB / 100, MAX_SCORE, gainScore);
        }

        for (int i = 0; i < scorePercentagesA.length; i++) {
            for (int j = 0; j < scorePercentagesB.length; j++) {
                answer += scorePercentagesA[i] * scorePercentagesB[j];
            }
        }

        return 1 - answer;
    }

    private double calculatePercentage(double percentage, int n, int r) {
        return calculateCombination(n, r) * Math.pow(percentage, r) * Math.pow(1 - percentage, n - r);
    }

    private long calculateCombination(int n, int r) {
        long[][] memo = new long[n + 1][r + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= Math.min(i, r); j++) {
                if (j == 0 || j == i) {
                    memo[i][j] = 1;
                } else {
                    // 파스칼 항등식
                    memo[i][j] = memo[i - 1][j - 1] + memo[i - 1][j];
                }
            }
        }

        return memo[n][r];
    }
}
