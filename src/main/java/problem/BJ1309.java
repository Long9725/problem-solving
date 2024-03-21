package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BJ1309 {
    private static int n;

    private static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(bf.readLine());
        dp = new int[n];

        if(n == 1) {
            System.out.println(3);
            return;
        }

        dp[0] = 3;
        dp[1] = 7;

        for (int i = 2; i < dp.length; i++) {
            dp[i] = (2 * dp[i - 1] + dp[i - 2]) % 9901;
        }

        System.out.println(dp[n - 1]);
    }
}
