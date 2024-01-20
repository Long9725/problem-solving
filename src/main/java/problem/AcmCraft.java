package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class AcmCraft {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        AcmCraftSolve solve = new AcmCraftSolve();
        int t = Integer.parseInt(st.nextToken());

        for (int i = 0; i < t; i++) {
            int n, k, w;
            int[] durations;
            int[][] orders;

            st = new StringTokenizer(bf.readLine());
            n = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());
            durations = new int[n];
            orders = new int[k][2];

            st = new StringTokenizer(bf.readLine());

            for (int j = 0; j < durations.length; j++) {
                durations[j] = Integer.parseInt(st.nextToken());
            }

            for (int j = 0; j < k; j++) {
                st = new StringTokenizer(bf.readLine());
                orders[j][0] = Integer.parseInt(st.nextToken());
                orders[j][1] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(bf.readLine());
            w = Integer.parseInt(st.nextToken());

            System.out.println(solve.solve(orders, durations, n, k, w));
        }
    }
}

class AcmCraftSolve {
    private List<List<AcmCraftNode>> nodes;

    private Queue<AcmCraftNode> queue;

    private int[] degrees;

    private int[] memo;

    public int solve(int[][] orders, int[] durations, int n, int k, int w) {
        /**
         * 예제 보니까 최종 노드랑 상관없는 노드를 제거하거나 고려하지 않아야 됌. => 나랑 연결된 노드만 신경쓰면 된다?
         * 예제 1에선 1부터 7까지 순서도가 필요함. 1 => 2 => 5 => 7 / 1 => 3 => 6 => 7, 두 경로 다 계산하기는 해야됌
         * 시간은 7 입장에선 5랑 6까지 가장 오래 걸리는 시간 중 하나를 고르고, 자기 자신 시간 더하면 끝. => dp?
         * dp[to] = Math.max(dp[to], dp[from] + durations[to-1]);
         * 경로 1 처리할 땐 0일테니까 업데이트 치고, 경로 2 처리할 땐 기존에 있는 데이터랑 비교하기
         * 경로는 위상정렬로 찾기
         * */

        nodes = new ArrayList<>();
        queue = new LinkedList<>();
        degrees = new int[n + 1];
        memo = new int[n + 1];

        // 그래프 세팅
        for (int i = 0; i <= n; i++) {
            nodes.add(new ArrayList<>());

            // dp 초깃값 세팅
            if (i > 0) {
                memo[i] = durations[i - 1];
            }
        }

        // 방향 그래프 및 degree 초기화
        for (int[] order : orders) {
            int from = order[0];
            int to = order[1];
            int toDuration = durations[to - 1];

            // 방향 그래프 초기화
            nodes.get(from).add(new AcmCraftNode(to, toDuration));

            // degree 초기화
            degrees[to]++;
        }

        // 위상 정렬에서 degree가 0으로 시작하는 노드들 전부 추가 => 시작점
        for (int i = 1; i < degrees.length; i++) {
            if (degrees[i] == 0) {
                queue.add(new AcmCraftNode(i, durations[i - 1]));
            }
        }

        // 위상 정렬
        while (!queue.isEmpty()) {
            AcmCraftNode node = queue.poll();
            List<AcmCraftNode> neighbors = nodes.get(node.vertex);

            // 인접 노드들의 degree 감소
            // 인접 노드들과의 건설 시간 비교
            for (AcmCraftNode neighbor : neighbors) {
                degrees[neighbor.vertex]--;
                memo[neighbor.vertex] = Math.max(memo[neighbor.vertex], memo[node.vertex] + neighbor.duration);

                // degree가 0이면 자기 주변 노드 추가
                if (degrees[neighbor.vertex] == 0) {
                    queue.add(neighbor);
                }
            }
        }

        return memo[w];
    }
}

class AcmCraftNode {
    final int vertex;

    final int duration;

    public AcmCraftNode(int vertex, int duration) {
        this.vertex = vertex;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "AcmCraftNode{" +
                "vertex=" + vertex +
                ", duration=" + duration +
                '}';
    }
}
