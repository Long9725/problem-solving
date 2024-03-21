package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BJ2116 {
    private static int n;

    private static int[][] arr;

    private static int max = Integer.MIN_VALUE;

    private static int diceLength = 6;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(bf.readLine());
        arr = new int[n][diceLength];

        for (int i = 0; i < n; i++) {
            arr[i] = Arrays.stream(bf.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        for (int i = 0; i < diceLength; i++) {
            dfs(1, arr[0][i], getMaxWithoutPair(i, 0));
        }
        /**
         * 1. 서로 붙어 있는 두 개의 주사위에서 아래에 있는 주사위의 윗면에 적혀있는 숫자는 위에 있는 주사위의 아랫면에 적혀있는 숫자와 같아야 한다.
         * 2. 단, 1번 주사위는 마음대로 놓을 수 있다.
         *
         * 윗면 아랫면 세트 => (0, 5), (1, 3), (2, 4)
         * */

        System.out.println(max);
    }

    private static void dfs(int depth, int target, int sum) {
        if (depth == n) {
            max = Math.max(max, sum);
            return;
        }

        int index = 0;

        for (int i = 0; i < diceLength; i++) {
            if (arr[depth][i] == target) {
                index = i;
                break;
            }
        }

        dfs(depth + 1, arr[depth][getPair(index)], sum + getMaxWithoutPair(index, depth));
    }

    private static int getPair(int target) {
        switch (target) {
            case 0:
                return 5;
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 1;
            case 4:
                return 2;
            default:
                return 0;
        }
    }

    private static int getMaxWithoutPair(int target, int depth) {
        int pair = getPair(target);
        int currentMax = Integer.MIN_VALUE;

        for (int i = 0; i < diceLength; i++) {
            if (i == target || i == pair) {
                continue;
            }

            currentMax = Math.max(currentMax, arr[depth][i]);
        }

        return currentMax;
    }
}
