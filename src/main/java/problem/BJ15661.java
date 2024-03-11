package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * <a href="https://www.acmicpc.net/problem/15661">백준 15661번 - 링크와 스타트</a>
 */
public class BJ15661 {
    private static int n;

    private static int[][] matrix;

    private static boolean[] visited;

    private static int count = 0;

    private static int result = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(bf.readLine());
        matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            matrix[i] = Arrays.stream(bf.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();;
        }

        visited = new boolean[n];

        /**
         * 4 <= N <= 20
         * 두 팀의 인원이 정확히 같을 필요는 없다. 대신 한 명 이상이어야 한다.
         * */

        for (int i = 1; i < n; i++) {
            count = i;
            dfs(0, 0);
        }

        System.out.println(result);
    }

    private static void dfs(int index, int depth) {
        if(depth == count) {
            check();
            return;
        }
        for (int i = index; i < n; i++) {
            if(visited[i]) continue;
            visited[i] = true;
            dfs(i + 1, depth + 1);
            visited[i] = false;
        }
    }

    private static void check() {
        int startTeam = 0;
        int linkTeam = 0;

        for(int i = 0; i < n-1; i++) {
            for(int j = i+1; j < n; j++) {
                if(visited[i] != visited[j]) {
                    continue;
                }
                if(visited[i]) {
                    startTeam += matrix[i][j] + matrix[j][i];
                } else {
                    linkTeam += matrix[i][j] + matrix[j][i];
                }
            }
        }

        result = Math.min(result,  Math.abs(startTeam - linkTeam));
    }
}
