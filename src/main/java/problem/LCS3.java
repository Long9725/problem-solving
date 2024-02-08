package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class LCS3 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int[][][] dp;
        int max = Integer.MIN_VALUE;
        String first = null;
        String second = null;
        String third = null;

        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(bf.readLine());

            if (i == 0) {
                first = st.nextToken();
            } else if (i == 1) {
                second = st.nextToken();
            } else {
                third = st.nextToken();
            }
        }

        dp = new int[first.length() + 1][second.length() + 1][third.length() + 1];

        for (int i = 1; i < first.length()+1; i++) {
            for (int j = 1; j < second.length()+1; j++) {
                for (int k = 1; k < third.length()+1; k++) {
                    if(first.charAt(i-1) == second.charAt(j-1) && second.charAt(j-1) == third.charAt(k-1)) {
                        dp[i][j][k] = dp[i-1][j-1][k-1] + 1;
                    } else {
                        dp[i][j][k] = Math.max(Math.max(dp[i-1][j][k], dp[i][j-1][k]), dp[i][j][k-1]);
                    }
                }
            }
        }

        System.out.println(dp[first.length()][second.length()][third.length()]);
    }
}
