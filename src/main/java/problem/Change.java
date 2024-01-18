package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Change {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int money = Integer.parseInt(st.nextToken());
        ChangeSolve solve = new ChangeSolve();
        int answer = solve.solve(money);
        System.out.println(answer);
    }
}

class ChangeSolve {
    public int solve(int money) {
        int[] dp = new int[100001];

        dp[0] = 0;
        dp[1] = 0;
        dp[2] = 1;
        dp[3] = 0;
        dp[4] = 2;
        dp[5] = 1;

        for (int i = 6; i <= money; i++) {
            if (dp[i - 2] > 0 && dp[i - 5] == 0) {
                dp[i] = dp[i - 2] + 1;
            } else if (dp[i - 2] == 0 && dp[i - 5] > 0) {
                dp[i] = dp[i - 5] + 1;
            } else {
                dp[i] = Math.min(dp[i - 2], dp[i - 5]) + 1;
            }
        }

        if (dp[money] == 0) {
            return -1;
        }

        return dp[money];
    }
}
