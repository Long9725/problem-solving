package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * <a href="https://www.acmicpc.net/problem/1750">백준 1750번 - 서로소의 개수</a>
 */
public class BJ1750 {
    private static int n;

    private static int[] sequence;

    // dp[i][j] = i번째 인덱스까지, 최대공약수가 j인 개수
    private static int[][] dp = new int[101][100001];

    private static int MOD = 10000003;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(bf.readLine());
        sequence = new int[n];

        for (int i = 0; i < n; i++) {
            sequence[i] = Integer.parseInt(bf.readLine());
        }

        Arrays.sort(sequence);

        // 여기 for문은 수열에서 i번째 원소를 볼 때
        for (int i = 0; i < n; i++) {
            // 수열의 원소는 자기자신이 최대공약수
            dp[i][sequence[i]] = 1;

            if(i == 0) continue;

            // 수열을 돌면서 이전 인덱스까지의 최대공약수가 j인 것을 포함
            for (int j = 1; j <= sequence[i]; j++) {
                dp[i][j] += dp[i - 1][j];
            }

            // 세팅이 전부 끝나고 이번엔 새로운 원소를 추가했을때를 구하기
            for (int j = 1; j <= sequence[i]; j++) {
                int newGcd = gcd(sequence[i], j);
                dp[i][newGcd] = (dp[i][newGcd] + dp[i - 1][j]) % MOD;
            }
        }

        System.out.println(dp[n - 1][1]);
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
