package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BJ15661_Bitmask {
    private static int n;
    private static int[][] matrix;
    private static int result = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(bf.readLine());
        matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            matrix[i] = Arrays.stream(bf.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        for (int i = 1; i < (1 << n) - 1; i++) {
            check(i);
        }

        System.out.println(result);
    }

    private static void check(int bitmask) {
        int startTeam = 0, linkTeam = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (contains(bitmask, 1 << i) == contains(bitmask, 1 << j)) {
                    if ((bitmask & (1 << i)) != 0) {
                        startTeam += matrix[i][j] + matrix[j][i];
                    } else {
                        linkTeam += matrix[i][j] + matrix[j][i];
                    }
                }
            }
        }

        result = Math.min(result, Math.abs(startTeam - linkTeam));
    }

    private static boolean contains(int leftBit, int rightBit) {
        return (leftBit & rightBit) == rightBit;
    }
}
