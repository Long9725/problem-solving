package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PalindromeFactory {
    static StringBuffer sb = new StringBuffer();

    static int[][] dp = new int[51][51];

    static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        sb.append(st.nextToken());

        // 1. 문자열의 어떤 위치에 어떤 문자를 삽입 (시작과 끝도 가능)
        // 2. 어떤 위치에 있는 문자를 삭제
        // 3. 어떤 위치에 있는 문자를 교환
        // 4. 서로 다른 문자를 교환 => 한 번만 사용 가능

        // dp[left][right] => 문자열 left 인덱스부터 right 인덱스까지의 부분 문자열을 Palindrome으로 만드는데 필요한 최소 연산 횟수
        // sb[left] == sb[right] => (left + 1, right - 1) 검사
        // sb[left] != sb[right] 일 때
        // left에 right와 같은 문자 추가 => (left, right - 1) 검사
        // right에 left와 같은 문자 추가 => (left + 1, right) 검사
        // left를 삭제 => (left + 1, right) 검사
        // right를 삭제 => (left, right - 1) 검사
        // left나 right 둘 중 하나 교환 => (left + 1, right - 1)
        // min(dp[left][right-1] + 1, dp[left+1][right] + 1, if(sb[left] == sb[right]) dp[left+1][right-1] else dp[left+1][right-1] + 1)
        // 4번 연산은 제일 처음에 동작 => 1번, 2번, 3번으로 4번 연산을 조합할 수 있기 때문

        initialDp();

        answer = solve(0, sb.length() - 1);

        for (int i = 0; i < sb.length() - 1; i++) {
            for (int j = i + 1; j < sb.length(); j++) {
                initialDp();
                swap(i, j);
                // 스왑 횟수 1개 추가
                answer = Math.min(answer, solve(0, sb.length() - 1) + 1);
                swap(i, j);
            }
        }

        System.out.println(answer);
    }

    private static void initialDp() {
        for (int i = 0; i <= 50; i++) {
            for (int j = 0; j <= 50; j++) {
                dp[i][j] = -1;
            }
        }
    }

    private static int solve(int left, int right) {
        if (dp[left][right] != -1) return dp[left][right];
        if (left >= right) return 0;

        int first = solve(left + 1, right) + 1;
        int second = solve(left, right - 1) + 1;
        int third = sb.charAt(left) == sb.charAt(right) ? solve(left + 1, right - 1) : solve(left + 1, right - 1) + 1;

        dp[left][right] = Math.min(Math.min(first, second), third);
        return dp[left][right];
    }

    private static void swap(int src, int dest) {
        char temp = sb.charAt(src);
        sb.setCharAt(src, sb.charAt(dest));
        sb.setCharAt(dest, temp);
    }
}
