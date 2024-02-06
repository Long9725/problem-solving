package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class IntegerTriangle {
    public static void main(String[] args) throws IOException {
        IntegerTriangleSolve solve = new IntegerTriangleSolve();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        List<List<Integer>> triangle = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            List<Integer> list = new ArrayList<>();
            st = new StringTokenizer(bf.readLine());

            while (st.hasMoreTokens()) {
                list.add(Integer.parseInt(st.nextToken()));
            }

            triangle.add(list);
        }

        System.out.println(solve.solve(n, triangle));
    }
}

class IntegerTriangleSolve {
    int n;

    int[] dx = {-1, 0};

    public int solve(int n, List<List<Integer>> triangle) {
        int max = Integer.MIN_VALUE;
        int[][] dp = new int[n][n];

        dp[0][0] = triangle.get(0).get(0);

        for (int i = 1; i < n; i++) {
            List<Integer> currents = triangle.get(i);

            for (int j = 0; j < currents.size(); j++) {
                int beforeLeft = j + dx[0];
                int beforeRight = j + dx[1];
                int beforeY = i - 1;

                if (beforeLeft < 0) {
                    dp[i][j] = currents.get(j) + dp[beforeY][beforeRight];
                } else {
                    dp[i][j] = currents.get(j) + Math.max(dp[beforeY][beforeLeft], dp[beforeY][beforeRight]);
                }
            }
        }

        for(int i = 0; i < n; i++) {
            if(max < dp[n-1][i]) {
                max = dp[n-1][i];
            }
        }

        return max;
    }
}
