package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class CitySplitPlan {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] edges = new int[m][3];
        CitySplitPlanSolve solve = new CitySplitPlanSolve();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(bf.readLine());

            for (int j = 0; j < edges[i].length; j++) {
                edges[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(solve.solve(edges, n, m));
    }
}

class CitySplitPlanSolve {
    public int solve(int[][] edges, int n, int m) {
        int answer = 0;
        int max = Integer.MIN_VALUE;
        int[] parent = new int[n+1];

        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        // 최소 신장 트리 만들고 가중치가 가장 높은거 끊어버리기
        // prim => (E + V) log V
        // kruskal => E log E or E log V
        // 간선 개수가 백만까지 가니까 kruskal?

        Arrays.sort(edges, Comparator.comparingInt(left -> left[2]));

        for (int i = 0; i < edges.length; i++) {
            int src = edges[i][0];
            int dest = edges[i][1];
            int weight = edges[i][2];

            if(find(parent, src) != find(parent, dest)) {
                if(weight > max) {
                    max = weight;
                }

                answer += weight;
                union(parent, src, dest);
            }
        }

        return answer - max;
    }

    private int find(int[] parent, int vertex) {
        if(parent[vertex] != vertex) {
            parent[vertex] = find(parent, parent[vertex]);
        }
        return parent[vertex];
    }

    private void union(int[] parent, int x, int y) {
        int xRoot = find(parent, x);
        int yRoot = find(parent, y);

        if(xRoot != yRoot) {
            parent[xRoot] = yRoot;
        }
    }
}
