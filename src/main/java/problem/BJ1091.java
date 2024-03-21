package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BJ1091 {
    private static int n;

    private static int[] p;

    private static int[] s;

    private static int[] current;

    private static int[] next;

    private static int result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(bf.readLine());
        p = Arrays.stream(bf.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        s = Arrays.stream(bf.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        current = new int[n];
        next = new int[n];

        /**
         * 각 카드가 어떤 플레이어에게 가야 하는지에 대한 정보는 길이가 N인 수열 P로 주어진다. 맨 처음 i번째 위치에 있던 카드를 최종적으로 플레이어 P[i] 에게 보내야한다.
         * => p를 012012 형태로 정렬한다.
         * */

        for (int i = 0; i < n; i++) {
            current[i] = p[i];
        }

        while (!isValid()) {
            result++;

            // 섞기
            for (int i = 0; i < current.length; i++) {
                next[s[i]] = current[i];
            }

            for (int i = 0; i < current.length; i++) {
                current[i] = next[i];
            }

            for (int i = 0; i < current.length; i++) {
                if(p[i] != current[i]) {
                    break;
                }

                if(i == current.length - 1) {
                    System.out.println(-1);
                    return;
                }
            }
        }

        System.out.println(result);
    }

    private static boolean isValid() {
        for (int i = 0; i < current.length; i++) {
            if (current[i] != i % 3) {
                return false;
            }
        }

        return true;
    }
}
