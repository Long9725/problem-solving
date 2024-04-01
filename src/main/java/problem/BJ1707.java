package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BJ1707 {
    public static void main(String[] args) throws IOException {
        BJ1707Solve solve = new BJ1707Solve();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int k = Integer.parseInt(bf.readLine());

        for (int i = 0; i < k; i++) {
            int[] inputs = Arrays.stream(bf.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int v = inputs[0];
            int e = inputs[1];
            int[][] edges = new int[e][2];

            for (int j = 0; j < e; j++) {
                edges[j] = Arrays.stream(bf.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            }

            solve.solution(v, edges);
        }
    }
}

class BJ1707Solve {
    private List<Integer>[] adjList;

    private Queue<Integer> queue;

    private int[] color;

    private static int NOT_MARKED = 0;

    private static int RED = 1;

    public void solution(int v, int[][] edges) {
        adjList = new ArrayList[v];
        queue = new LinkedList<>();
        color = new int[v];

        for (int i = 0; i < v; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < edges.length; i++) {
            int src = edges[i][0] - 1;
            int dest = edges[i][1] - 1;

            adjList[src].add(dest);
            adjList[dest].add(src);
        }

        for (int i = 0; i < v; i++) {
            if(color[i] != 0) {
                continue;
            }
            try {
                bfs(i);
            } catch (IllegalArgumentException e) {
                System.out.println("NO");
                return;
            }
        }

        System.out.println("YES");
    }

    private void bfs(int index) {
        queue.add(index);
        color[index] = BJ1707Solve.RED;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (Integer next : adjList[current]) {
                if(color[next] == BJ1707Solve.NOT_MARKED) {
                    queue.offer(next);
                    color[next] = color[current] * -1;
                } else if(color[current] + color[next] != 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }
}