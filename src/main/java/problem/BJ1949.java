package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BJ1949 {
    private static int n;
    private static int[] countVillagers;

    private static ArrayList<Integer>[] adj;

    private static int[][] dp;

    private static int NOT_EXCELLENT = 0;
    private static int IS_EXCELLENT = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(bf.readLine());
        countVillagers = Arrays.stream(bf.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        adj = new ArrayList[n];
        dp = new int[n][2];

        /**
         * 1. '우수 마을'로 선정된 마을 주민 수의 총 합을 최대로 해야 한다.
         * 2. 마을 사이의 충돌을 방지하기 위해서, 만일 두 마을이 인접해 있으면 두 마을을 모두 '우수 마을'로 선정할 수는 없다. 즉 '우수 마을'끼리는 서로 인접해 있을 수 없다.
         * 3. 선정되지 못한 마을에 경각심을 불러일으키기 위해서, '우수 마을'로 선정되지 못한 마을은 적어도 하나의 '우수 마을'과는 인접해 있어야 한다.
         *
         * 1. n번째 마을이 우수마을이라면 n+1번째는 우수마을이 될 수 없다.
         * 2. n번째 마을이 우수마을이 아니라면
         *      2-1. n+1번째 마을이 우수마을이다.
         *      2-2. n+1번째 마을이 우수마을이 아니고 n+2번째 마을이 우수 마을이다. => 1번 조건 때문에 적용. 3번도 자연스럽게 해결
         * */

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        /**
         * 트리 초기화
         * */
        for (int i = 0; i < n - 1; i++) {
            int[] edge = Arrays.stream(bf.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int src = edge[0] - 1;
            int dest = edge[1] - 1;

            adj[src].add(dest);
            adj[dest].add(src);
        }

        /**
         * 바텀업 시작
         * */
        dfs(0, -1);

        System.out.println(Math.max(dp[0][NOT_EXCELLENT], dp[0][IS_EXCELLENT]));
    }


    private static void dfs(int node, int parent) {
        /**
         * 부모가 아닐 때는 dp 계산
         * */
        for (Integer adjNode : adj[node]) {
            if(adjNode != parent) {
                dfs(adjNode, node);
                dp[node][NOT_EXCELLENT] += Math.max(dp[adjNode][NOT_EXCELLENT], dp[adjNode][IS_EXCELLENT]);
                dp[node][IS_EXCELLENT] += dp[adjNode][NOT_EXCELLENT];
            }
        }

        /**
         * 현재 노드가 우수 마을인 경우 마지막에 자기 마을 주민 수 추가
         * */
        dp[node][IS_EXCELLENT] += countVillagers[node];
    }
}
