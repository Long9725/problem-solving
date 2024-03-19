package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BJ16987 {
    private static final int HEALTH = 0;

    private static final int WEIGHT = 1;

    private static int n;

    private static int[][] arr;

    private static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(bf.readLine());
        arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i] = Arrays.stream(bf.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        if(n == 1) {
            System.out.println(0);
            return;
        }

        dfs(0, 0);

        System.out.println(max);
    }

    private static void dfs(int depth, int count) {
        if (depth == n) {
            max = Math.max(max, count);
            return;
        }

        if (arr[depth][HEALTH] <= 0) {
            dfs(depth + 1, count);
            return;
        }

        for (int i = 0; i < n; i++) {
            if (i == depth) {
                continue;
            }

            if (arr[depth][HEALTH] <= 0 || arr[i][HEALTH] <= 0) {
                dfs(depth + 1, count);
                continue;
            }

            arr[depth][HEALTH] -= arr[i][WEIGHT];
            arr[i][HEALTH] -= arr[depth][WEIGHT];

            if (arr[depth][HEALTH] <= 0 && arr[i][HEALTH] <= 0) {
                dfs(depth + 1, count + 2);
            } else if (arr[depth][HEALTH] <= 0 || arr[i][HEALTH] <= 0) {
                dfs(depth + 1, count + 1);
            } else {
                dfs(depth + 1, count);
            }

            arr[depth][HEALTH] += arr[i][WEIGHT];
            arr[i][HEALTH] += arr[depth][WEIGHT];
        }
    }
}