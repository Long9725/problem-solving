package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class FindRoute {
    public static void main (String[] args) throws IOException {
        FindRouteSolve solve = new FindRouteSolve();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int[][] graph = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());
            for (int j = 0; j < n; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] result = solve.solve(graph, n);

        for (int[] ints : result) {
            StringBuilder print = new StringBuilder();
            for (int anInt : ints) {
                print.append(anInt).append(" ");
            }
            System.out.println(print.toString().trim());
        }
    }

}

class FindRouteSolve {
    public int[][] solve(int[][] graph, int n) {
        // 시작에서 중간을 갈 수 있고 중간에서 마지막을 갈 수 있다면, 시작에서 마지막까지 갈 수 있는 길이 존재
        for(int i = 0; i < n; i++) { // 중간
            for(int j = 0; j < n; j++) { // 시작
                // 애초에 시작에서 중간을 못 가면 탐색할 필요 없음
                if(graph[j][i] == 0) {
                    continue;
                }

                for(int k = 0; k < n; k++) { // 마지막
                    // 중간에서 마지막을 갈 수 있다면, 시작에서 마지막까지 갈 수 있는 길이 존재
                    if(graph[i][k] == 1) {
                        graph[j][k] = 1;
                    }
                }
            }
        }

        return graph;
    }
}
