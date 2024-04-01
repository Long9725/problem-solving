package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BJ1389 {
    private static int n;

    private static int m;

    private static Set<Integer>[] adjList;

    private static int[][] map;

    private static Queue<BJ1389Node> queue = new LinkedList<>();

    private static int min = Integer.MAX_VALUE;

    private static int result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int[] inputs = Arrays.stream(bf.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        n = inputs[0];
        m = inputs[1];
        map = new int[n][n];
        adjList = new Set[n];

        /**
         * * 친구 관계는 중복되어 들어올 수도 있으며, 친구가 한 명도 없는 사람은 없다
         * * 모든 사람은 친구 관계로 연결되어져 있다
         *
         * 1. 케빈 베이컨을 저장할 2차원 배열 DB 생성
         * 2. 인접 리스트를 가져와서 bfs
         * */

        for (int i = 0; i < m; i++) {
            int[] edge = Arrays.stream(bf.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int src = edge[0] - 1;
            int dest = edge[1] - 1;

            setAdj(src, dest);
            setAdj(dest, src);
        }

        // 모든 사람은 친구 관계로 연결되어져 있다 => 0부터 시작해도 bfs 가능

        for (int i = 0; i < n; i++) {
            int depth = 0;
            boolean[] visited = new boolean[n];

            queue.add(new BJ1389Node(i, depth));
            visited[i] = true;

            while (!queue.isEmpty()) {
                BJ1389Node current = queue.poll();
                Set<Integer> adj = adjList[current.node];

                map[i][current.node] = current.depth;
                map[current.node][i] = current.depth;

                for (int next : adj) {
                    if (!visited[next]) {
                        visited[next] = true;
                        int nextDepth = current.depth + 1;
                        queue.add(new BJ1389Node(next, nextDepth));
                    }
                }
            }
        }

        for (int i = 0; i < map.length; i++) {
            int currentMin = Arrays.stream(map[i]).sum();

            if (min > currentMin) {
                min = currentMin;
                result = i;
            }
        }

        System.out.println(result + 1);
    }


    private static void setAdj(int src, int dest) {
        Set<Integer> adj = adjList[src];

        if (adj == null) {
            adj = new HashSet<>();
            adjList[src] = adj;
        }

        adj.add(dest);
    }
}

class BJ1389Node {
    int node;

    int depth;

    public BJ1389Node(int node, int depth) {
        this.node = node;
        this.depth = depth;
    }
}
