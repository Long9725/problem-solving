package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class OrganicCabbage {
    public static void main(String[] args) throws IOException {
        OrganicCabbageSolve solve = new OrganicCabbageSolve();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int t;

        t = Integer.parseInt(st.nextToken());

        for (int i = 0; i < t; i++) {
            int m, n, k;
            int[][] edges;

            st = new StringTokenizer(bf.readLine());
            m = Integer.parseInt(st.nextToken());
            n = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());
            edges = new int[k][2];

            for (int j = 0; j < k; j++) {
                st = new StringTokenizer(bf.readLine());
                edges[j][0] = Integer.parseInt(st.nextToken());
                edges[j][1] = Integer.parseInt(st.nextToken());
            }

            System.out.println(solve.solve(m, n, k, edges));
        }

    }
}

class OrganicCabbageSolve {
    // 상하좌우
    private int[] xMoves = {0, 0, -1, 1};
    private int[] yMoves = {-1, 1, 0, 0};

    private int n;

    private int m;

    private int k;

    private int[][] edges;

    private boolean[][] graph;

    private boolean[][] visited;

    public int solve(int m, int n, int k, int[][] edges) {
        int answer = 0;
        this.n = n;
        this.m = m;
        this.k = k;
        this.edges = edges;
        this.graph = new boolean[n][m];
        this.visited = new boolean[n][m];

        for (int[] edge : edges) {
            int y = edge[1];
            int x = edge[0];
            graph[y][x] = true;
        }

        for (int y = 0; y < n; y++) {
            for (int x = 0; x < m; x++) {
                // 배추가 있으면서 아직 방문하지 않은 곳 탐색
                if (!visited[y][x] && graph[y][x]) {
                    Queue<OrganicCabbageCoordinate> queue = new LinkedList<>();
                    OrganicCabbageCoordinate coordinate = new OrganicCabbageCoordinate(x, y);

                    answer++;
                    queue.add(coordinate);

                    while(!queue.isEmpty()) {
                        OrganicCabbageCoordinate current = queue.poll();

                        for (int index = 0; index < xMoves.length; index++) {
                            OrganicCabbageCoordinate next = new OrganicCabbageCoordinate(current.x + xMoves[index], current.y + yMoves[index]);

                            if(next.existCabbage(m, n, graph) && !visited[next.y][next.x]) {
                                queue.add(next);
                                visited[next.y][next.x] = true;
                            }
                        }
                    }
                }
            }
        }

        return answer;
    }
}

class OrganicCabbageCoordinate {
    final int x;

    final int y;

    public OrganicCabbageCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean existCabbage(int m, int n, boolean[][] graph) {
        // 가로가 영역 밖임
        if (x < 0 || x >= m) {
            return false;
        }

        // 세로가 영역 밖임
        if (y < 0 || y >= n) {
            return false;
        }

        return graph[y][x];
    }
}