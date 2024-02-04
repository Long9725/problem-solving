package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class RgbDistance {
    public static void main(String[] args) throws IOException {
        RgbDistanceSolve solve = new RgbDistanceSolve();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int[][] rgbs = new int[n][3];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());

            rgbs[i][0] = Integer.parseInt(st.nextToken());
            rgbs[i][1] = Integer.parseInt(st.nextToken());
            rgbs[i][2] = Integer.parseInt(st.nextToken());
        }

        System.out.println(solve.solve(n, rgbs));
    }
}

class RgbDistanceSolve {
    int n;

    int[][] rgbs;

    int[][] memo;

    public int solve(int n, int[][] rgbs) {
        // 이웃한 집의 색과 같으면 안된다.
        // 완탐은 3 * 2^(n-1) 이라 안된다.
        // 그럼 dp?
        this.n = n;
        this.rgbs = rgbs;
        memo = new int[n][3];

        for (int i = 0; i < 3; i++) {
            memo[0][i] = rgbs[0][i];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < 3; j++) {
                if(j == 0) {
                    memo[i][j] = Math.min(memo[i-1][1], memo[i-1][2]) + rgbs[i][j];
                } else if(j == 1) {
                    memo[i][j] = Math.min(memo[i-1][0], memo[i-1][2]) + rgbs[i][j];
                } else {
                    memo[i][j] = Math.min(memo[i - 1][0], memo[i - 1][1]) + rgbs[i][j];
                }
            }
        }

        return Arrays.stream(memo[n - 1]).min().getAsInt();
    }
}
