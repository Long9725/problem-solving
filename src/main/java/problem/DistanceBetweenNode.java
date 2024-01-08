package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class DistanceBetweenNode {
    static int _n, _m;
    static int[][] graph;

    static boolean[] visit;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(tokenizer.nextToken());
        int m = Integer.parseInt(tokenizer.nextToken());
        int[][] nodesWithDistance = new int[n - 1][3];
        int[][] calculateDistanceBetweenNodes = new int[m][2];

        for (int i = 0; i < n - 1; i++) {
            StringTokenizer distanceTokenizer = new StringTokenizer(bf.readLine());
            int from = Integer.parseInt(distanceTokenizer.nextToken());
            int to = Integer.parseInt(distanceTokenizer.nextToken());
            int distance = Integer.parseInt(distanceTokenizer.nextToken());

            nodesWithDistance[i][0] = from;
            nodesWithDistance[i][1] = to;
            nodesWithDistance[i][2] = distance;
        }

        for (int i = 0; i < m; i++) {
            StringTokenizer distanceTokenizer = new StringTokenizer(bf.readLine());
            int from = Integer.parseInt(distanceTokenizer.nextToken());
            int to = Integer.parseInt(distanceTokenizer.nextToken());

            calculateDistanceBetweenNodes[i][0] = from;
            calculateDistanceBetweenNodes[i][1] = to;
        }

        calculate(nodesWithDistance, calculateDistanceBetweenNodes, n, m);
    }

    public static void calculate(int[][] nodesWithDistance, int[][] calculateDistanceBetweenNodes, int n, int m) {
        _n = n;
        _m = m;
        graph = new int[n][n];

        for (int i = 0; i < nodesWithDistance.length; i++) {
            int from = nodesWithDistance[i][0];
            int to = nodesWithDistance[i][1];
            int distance = nodesWithDistance[i][2];

            graph[to - 1][from - 1] = distance;
            graph[from - 1][to - 1] = distance;
        }

        for (int i = 0; i < _m; i++) {
            visit = new boolean[_n];
            int start = calculateDistanceBetweenNodes[i][0] - 1;
            int end = calculateDistanceBetweenNodes[i][1] - 1;

            visit[start] = true;

            dfs(start, end, 0);
        }

//        for (int i = 0; i < _m; i++) {
//            visit = new boolean[_n];
//            int start = calculateDistanceBetweenNodes[i][0] - 1;
//            int end = calculateDistanceBetweenNodes[i][1] - 1;
//
//            bfs(start, end);
//        }
    }

    public static void dfs(int vertex, int end, int currentDistance) {
        if (vertex == end) {
            System.out.println(currentDistance);
            return;
        }

        for (int i = 0; i < _n; i++) {
            if (graph[vertex][i] > 0 && !visit[i]) {
                visit[i] = true;
                dfs(i, end, currentDistance + graph[vertex][i]);
                visit[i] = false;
            }
        }
    }


    public static void bfs(int start, int end) {
        Queue<int[]> queue = new LinkedList<>();

        visit[start] = true;

        queue.add(new int[]{start, 0});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currentNode = current[0];
            int currentDistance = current[1];

            if (currentNode == end) {
                System.out.println(currentDistance);
                return;
            }

            for (int i = 0; i < _n; i++) {
                if (graph[currentNode][i] > 0 && !visit[i]) {
                    visit[i] = true;
                    queue.add(new int[]{i, currentDistance + graph[currentNode][i]});
                }
            }
        }
    }
}
