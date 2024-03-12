package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * <a href="https://www.acmicpc.net/problem/2533">백준 2533번 - 사회망 서비스(SNS)</a>
 */
public class BJ2533 {
    private static int n;

    private static List<Integer>[] adjList;

    private static int[][] dp;

    private static boolean[] visited;

    private static int root;

    private static int NOT_EARLY_ADAPTER = 0;

    private static int IS_EARLY_ADAPTER = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(bf.readLine());
        adjList = new ArrayList[n];
        visited = new boolean[n];
        dp = new int[n][2];

        for (int i = 0; i < n-1; i++) {
            int[] edge = Arrays.stream(bf.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int src = edge[0] - 1;
            int dest = edge[1] - 1;

            setEdge(src, dest);
            setEdge(dest, src);
        }

        /**
         * 1. 내가 얼리어답터라면 => 내 인접 친구들의 얼리어답터 최소 갯수에다가 1 추가
         * 2. 내가 얼리어답터가 아니라면 => 내 인접 친구들의 얼리어답터 최소 갯수를 그대로 받음
         *
         * dp[i][j] => i번째 친구를 기준으로 파악, j번째는 해당 친구가 얼리어답터인지 아닌지 여부
         * 초기값 => dp[0][0] = 0, dp[0][1] = 1
         * dp[i][0] = for (friend in adjList[i]) sum from dp[friend][1] => dp[i][0] = sum
         * dp[i][1] = for (friend in adjList[i]) sum from (min from (dp[friend][0], dp[friend][1])) => dp[i][1] = sum + 1
         *
         * 이 개념을 트리에다가 적용시키기?
         * 바텀업이니까 트리 leaf 부터 시작하기
         *
         * search(int node) {
         *  for(friend in adjList[node]) {
         *      search(friend)
         *  }
         *
         *  dp[node][0] = for (friend in adjList[i]) sum from dp[friend][1];
         *  dp[node][1] = (for (friend in adjList[i]) sum from (min from (dp[friend][0], dp[friend][1]))) + 1;
         * }
         *
         * 트리라고 해서 루트를 찾을 필요는 X 루트가 누군지 정해주지 않았음. 그냥 싸이클 없는 연결 그래프
         * */

        search(root);

        System.out.println(Math.min(dp[root][0], dp[root][1]));
    }

    private static void setEdge(int src, int dest) {
        List<Integer> srcAdjList = adjList[src];

        if (srcAdjList == null) {
            srcAdjList = new ArrayList<>();
        }

        srcAdjList.add(dest);

        adjList[src] = srcAdjList;
    }

    private static void search(int node) {
        visited[node] = true;

        dp[node][NOT_EARLY_ADAPTER] = 0;
        dp[node][IS_EARLY_ADAPTER] = 1;

        for (Integer friend : adjList[node]) {
            if(visited[friend]) continue;

            search(friend);

            dp[node][NOT_EARLY_ADAPTER] += dp[friend][IS_EARLY_ADAPTER];
            dp[node][IS_EARLY_ADAPTER] += Math.min(dp[friend][NOT_EARLY_ADAPTER], dp[friend][IS_EARLY_ADAPTER]);
        }
    }
}