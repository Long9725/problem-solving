package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SubStringDrawGame {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        SubStringDrawGameSolve solve = new SubStringDrawGameSolve();

        System.out.println(solve.solve(n));
    }
}

class SubStringDrawGameSolve {
    public int solve(int n) {
        if (n < 10) {
            return -1;
        }

        int[] dp = new int[n + 1];

        dp[10] = 1;

        for (int i = 10; i <= n; i++) {
            Set<Integer> substringSet = generateSubset(i);
            Iterator<Integer> iterator = substringSet.iterator();
            int min = Integer.MAX_VALUE;

            while (iterator.hasNext()) {
                int nextValue = iterator.next();

                // 다음 게임 패배 => 현재 게임 승리
                if (dp[i - nextValue] == 0) {
                    min = Math.min(min, nextValue);
                }
            }

            // 현재 게임을 승리하는 방법을 찾았다는 뜻
            if (min != Integer.MAX_VALUE) {
                dp[i] = min;
            }
        }

        // n의 진 부분 문자열 중 양의 정수 M을 고른다. 그리고 N에서 M을 뺀다.
        // N-M이 다음 숫자가 된다.
        // 두명이서 번갈아 하고 A가 먼저 시작.
        // 내가 A 일 때 승리를 위해서 골라야 하는 수를 고르기. 여러 개면 가장 작은거 만약 없으면 -1
        // 그럼 일단 매 턴마다 진 부분 문자열을 구하는 함수 만들기. V
        // 끝까지 경기를 해봐야 이겼는지 졌는지를 알 수 있으니까 dfs. => 근데 상대방이 악수를 둔다는 보장이 없잖아?
        // 시작 조건에 따라 다르니까 첫 부분 문자열을 순회하면서 dfs. => 근데 상대방이 악수를 둔다는 보장이 없잖아?
        // 만약 한자리 숫자면 게임이 진행 불가능함. V
        // 두 자리 숫자이면 항상 승리
        // 서로 최선의 수룰 계속해서 고르려고 한다? 실수 없이?

        return dp[n] == 0 ? -1 : dp[n];
    }

    private Set<Integer> generateSubset(int n) {
        Set<Integer> result = new HashSet<>();
        String string = String.valueOf(n);

        if (n < 10) {
            return result;
        }

        for (int i = 0; i < string.length(); i++) {
            for (int j = i; j < string.length(); j++) {
                String substring = string.substring(i, j + 1);
                int value = Integer.parseInt(substring);

                if (substring.length() == string.length() || value == 0) {
                    continue;
                }

                result.add(value);
            }
        }

        return result;
    }
}
