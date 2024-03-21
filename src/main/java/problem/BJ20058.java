package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class BJ20058 {
    private static int n;

    private static int q;

    private static int pow;

    private static int[][] a;

    private static int[] l;

    private static int[][] coordinates = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };

    private static boolean[][] visited;

    private static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int[] arr = Arrays.stream(bf.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        n = arr[0];
        q = arr[1];
        pow = (int) Math.pow(2, n);
        a = new int[pow][pow];
        visited = new boolean[pow][pow];

        for (int i = 0; i < pow; i++) {
            a[i] = Arrays.stream(bf.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        l = Arrays.stream(bf.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        for (int level : l) {
            List<int[]> meltedCoordinates = new ArrayList<>();

            /**
             * 1. 파이어스톰은 먼저 격자를 2L × 2L 크기의 부분 격자로 나눈다.
             * 2. 그 후, 모든 부분 격자를 시계 방향으로 90도 회전시킨다.
             * */
            divide(level);

            // 3. 이후 얼음이 있는 칸 3개 또는 그 이상과 인접해있지 않은 칸은 얼음의 양이 1 줄어든다.
            for (int i = 0; i < pow; i++) {
                for (int j = 0; j < pow; j++) {
                    if (a[i][j] > 0 && mustMelt(j, i)) {
                        meltedCoordinates.add(new int[]{i, j});
                    }
                }
            }

            for (int[] meltedCoordinate : meltedCoordinates) {
                int x = meltedCoordinate[1];
                int y = meltedCoordinate[0];

                if (a[y][x] > 0) {
                    a[y][x]--;
                }
            }
        }

        // 1. 남아있는 얼음 A[r][c]의 합
        // 2. 남아있는 얼음 중 가장 큰 덩어리가 차지하는 칸의 개수
        for (int i = 0; i < pow; i++) {
            for (int j = 0; j < pow; j++) {
                if (!visited[i][j] && a[i][j] > 0) {
                    visited[i][j] = true;
                    max = Math.max(max, dfs(j, i));
                }
            }
        }

        System.out.println(Arrays.stream(a).mapToInt(row -> Arrays.stream(row).sum()).sum());
        System.out.println(max);
    }

    private static int dfs(int x, int y) {
        int count = 1;

        for (int i = 0; i < coordinates.length; i++) {
            int nextX = x + coordinates[i][0];
            int nextY = y + coordinates[i][1];

            if (nextX < 0 || nextX >= pow || nextY < 0 || nextY >= pow) {
                continue;
            }

            if (visited[nextY][nextX]) {
                continue;
            }
            if (a[nextY][nextX] <= 0) {
                continue;
            }

            visited[nextY][nextX] = true;

            count += dfs(nextX, nextY);
        }

        return count;
    }

    private static boolean mustMelt(int x, int y) {
        int count = 0;

        for (int i = 0; i < coordinates.length; i++) {
            int nextX = x + coordinates[i][0];
            int nextY = y + coordinates[i][1];

            if (nextX < 0 || nextX >= pow || nextY < 0 || nextY >= pow) {
                continue;
            }
            if (a[nextY][nextX] > 0) {
                count++;
            }
        }

        if (count < 3) {
            return true;
        }

        return false;
    }

    private static void divide(int level) {
        if (level == 0 || level > n) {
            return;
        }

        int levelPow = (int) Math.pow(2, level);

        /**
         * 1. 파이어스톰은 먼저 격자를 2L × 2L 크기의 부분 격자로 나눈다.
         * */
        for (int i = 0; i < pow; i += levelPow) {
            for (int j = 0; j < pow; j += levelPow) {
                /**
                 * 2. 그 후, 모든 부분 격자를 시계 방향으로 90도 회전시킨다.
                 * */
                rotate(i, j, level);
            }
        }
    }

    private static void rotate(int x, int y, int level) {
        int levelPow = (int) Math.pow(2, level);
        int[][] temp = new int[levelPow][levelPow];

        for (int i = 0; i < levelPow; i++) {
            for (int j = 0; j < levelPow; j++) {
                temp[j][levelPow - 1 - i] = a[x + i][y + j];
            }
        }

        for (int i = 0; i < levelPow; i++) {
            for (int j = 0; j < levelPow; j++) {
                a[x + i][y + j] = temp[i][j];
            }
        }
    }
}
