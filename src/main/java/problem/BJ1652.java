package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class BJ1652 {
    private static int n;

    private static String[][] arr;

    private static int row = 0;

    private static int col = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(bf.readLine());
        arr = new String[n][n];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = bf.readLine().split("");
        }

        /**
         * 1. 똑바로 연속해서 2칸 이상의 빈 칸이 존재하면 그 곳에 몸을 양 옆으로 쭉 뻗으면서 누울 수 있다.
         * 2. 가로로 누울 수도 있고 세로로 누울 수도 있다
         * 3. 누울 때는 무조건 몸을 쭉 뻗기 때문에 반드시 벽이나 짐에 닿게 된다. (중간에 어정쩡하게 눕는 경우가 없다.)
         * */

        for (int i = 0; i < n; i++) {
            int count = 0;

            for (int j = 0; j < n; j++) {
                if (Objects.equals(arr[i][j], ".")) {
                    count++;

                    // 벽 계산
                    if (j == n - 1 && count >= 2) {
                        row++;
                    }
                    continue;
                }
                if (count >= 2) {
                    row++;
                }

                count = 0;
            }
        }

        for (int i = 0; i < n; i++) {
            int count = 0;

            for (int j = 0; j < n; j++) {
                if (Objects.equals(arr[j][i], ".")) {
                    count++;

                    // 벽 계산
                    if (j == n - 1 && count >= 2) {
                        col++;
                    }
                    continue;
                }

                if (count >= 2) {
                    col++;
                }

                count = 0;
            }
        }

        System.out.println(row + " " + col);
    }
}
