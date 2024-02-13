package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 백준 1563번
 * <p>
 * 개근상
 */
public class BJ1563 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int[][][] dp = new int[n+1][3][2];
        int div = 1000000;
        int result;

        // 개근상을 못 받는 기준
        // 1. 지각을 두 번 이상 => 1. 지각 한 번까지는 개근상
        // 2. 결석을 세 번 연속 => 2. 결석이 세 번 연속만 아니면 개근상
        // 각 자리마다 3가지 경우의 수, N <= 1000이므로 완탐은 3^1000

        /**
         * dp[문자열 길이][연속된 A의 개수][L이 포함된 횟수]
         *
         * 각 케이스에 해당하는 이전 케이스들 값 더하고 div로 나누기
         *
         * ex) O로 시작 => OO, OA, OL
         *
         * dp[i][0][0] => 연속된 A가 없고 L도 사용한 적이 없다. => 직전 문자열도 L을 사용한 적이 없어야 한다.
         *      - dp[i-1][0][0] => 직전이 O, 현재 O
         *      - dp[i-1][1][0] => 직전이 A, 현재 O
         *      - dp[i-1][2][0] => 직전과 두 번 전이 A, 현재 O
         * dp[i][1][0] => 연속된 A가 하나 있고 L은 사용한 적이 없다. => 직전 문자열은 A와 L을 사용한 적이 없어야 한다.
         *      - dp[i-1][0][0] => 직전이 O, 현재 A
         * dp[i][2][0] => 연속된 A가 두 개 있고 L은 사용한 적이 없다. => 직전 문자열은 A를 한 개 사용했고 L을 사용한 적이 없어야 한다.
         *      - dp[i-1][1][0] => 직전이 A, 현재 A
         * dp[i][0][1] => 연속된 A가 없고 L은 사용한 적이 있다. => 직전 문자열에 따라 다르다.
         *      - dp[i-1][0][0] => 직전이 O이고 L이 없다 => 현재 L
         *      - dp[i-1][1][0] => 직전이 A이고 L이 없다 => 현재 L
         *      - dp[i-1][2][0] => 직전이 A이고 L이 없다 => 현재 L
         *      - dp[i-1][0][1] => 직전이 O나 L이고, L이 있다 => 현재 O
         *      - dp[i-1][1][1] => 직전이 A이고 L이 있다 => 현재 O
         *      - dp[i-1][2][1] => 직전이 A이고 L이 있다 => 현재 O
         * dp[i][1][1] => 연속된 A가 하나 있고 L은 하나 있다 => 직전 문자열은 O나 L로 끝나야 한다.
         *      - dp[i-1][0][1] => 직전이 O이고 L이 있다 => 현재 A
         * dp[i][2][1] => 연속된 A가 두개 있고 L은 하나 있다 => 직전 문자열은 A로 끝나고 L이 있어야 한다.
         *      - dp[i-1][1][1] => 직전이 A이고 L이 있따 => 현재 A
         *
         * 초깃값
         * dp[1][0][0] => O로 시작 => 1
         * dp[1][1][0] => A로 시작 => 1
         * do[1][0][1] => L로 시작 => 1
         * */

        dp[1][0][0] = 1;
        dp[1][1][0] = 1;
        dp[1][0][1] = 1;

        for(int i = 2; i <= n; i++) {
            dp[i][0][0] = (dp[i-1][0][0] + dp[i-1][1][0] + dp[i-1][2][0]) % div;
            dp[i][1][0] = dp[i-1][0][0] % div;
            dp[i][2][0] = dp[i-1][1][0] % div;
            dp[i][0][1] = (dp[i-1][0][0] + dp[i-1][1][0] + dp[i-1][2][0] + dp[i-1][0][1] + dp[i-1][1][1] + dp[i-1][2][1]) % div;
            dp[i][1][1] = dp[i-1][0][1] % div;
            dp[i][2][1] = dp[i-1][1][1] % div;
        }

        result = (dp[n][0][0] + dp[n][1][0] + dp[n][2][0] + dp[n][0][1] + dp[n][1][1] + dp[n][2][1]) % div;

        System.out.println(result);
    }
}
