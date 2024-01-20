package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Coins {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int t = Integer.parseInt(st.nextToken());
        CoinsSolve solve = new CoinsSolve();

        for (int i = 0; i < t; i++) {
            int n;
            int[] currencies;
            int money;

            st = new StringTokenizer(bf.readLine());
            n = Integer.parseInt(st.nextToken());
            currencies = new int[n];

            st = new StringTokenizer(bf.readLine());

            for (int j = 0; j < currencies.length; j++) {
                currencies[j] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(bf.readLine());
            money = Integer.parseInt(st.nextToken());
            System.out.println(solve.solve(n, currencies, money));
        }
    }
}

class CoinsSolve {
    public int solve(int n, int[] currencies, int money) {
        int[][] dp = new int[currencies.length][money + 1];

        // 모든 금액을 첫 번째 동전만 사용해서 만들 수 있는 방법의 수로 초기화
        // 0원은 아무 동전도 안써도 가능하니까 무조건 1
        for (int i = 0; i <= money; i++) {
            if (i % currencies[0] == 0) {
                dp[0][i] = 1;
            }
        }

        for (int i = 1; i < currencies.length; i++) {
            for (int j = 0; j <= money; j++) {
                // 현재 동전을 사용하지 않는 경우 => 기본적으로 위에 있는 처리 결과를 받아줘야 return에서 첫번째 동전만 사용하더라도 결과를 전달 가능
                dp[i][j] = dp[i - 1][j];

                // 현재 동전을 사용하는 경우 => 같은 화폐의 이전 데이터 사용
                if (j >= currencies[i]) {
                    dp[i][j] += dp[i][j - currencies[i]];
                }
            }
        }

        return dp[currencies.length - 1][money];
    }
}
