package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BJ10819 {
    private static int n;

    private static int[] arr;

    private static boolean[] visited;

    private static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(bf.readLine());
        arr = Arrays.stream(bf.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        visited = new boolean[n];

        dfs(0, new int[n]);

        System.out.println(max);
    }

    private static void dfs(int depth, int[] temp) {
        if(depth == n) {
            int sum = 0;

            for (int i = 0; i < temp.length - 1; i++) {
                sum += Math.abs(temp[i] - temp[i + 1]);
            }

            max = Math.max(sum, max);
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            if(!visited[i]) {
                visited[i] = true;
                temp[depth] = arr[i];
                dfs(depth + 1, temp);
                visited[i] = false;
            }
        }
    }
}
