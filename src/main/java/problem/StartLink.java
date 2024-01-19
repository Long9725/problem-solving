package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class StartLink {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int f = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int g = Integer.parseInt(st.nextToken());
        int u = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        StartLinkSolve solve = new StartLinkSolve();
        System.out.println(solve.solve(f, s, g, u, d));
    }
}

class StartLinkSolve {
    public String solve(int f, int s, int g, int u, int d) {
        // 1 <= s
        // g <= f <= 1000000
        // 0 <= u, d <= 1000000
        // 가장 높은 층은 f
        // 현재 s층 => 엘리베이터를 타고 g층으로 이동
        // u 버튼은 위로 u만큼, d 버튼은 아래로 d만큼
        // 최소 몇 번 => bfs 한 번 해보기?

        Queue<Integer> queue = new LinkedList<>();
        int[] visited = new int[f + 1];
        int[] values = {u, -d};

        visited[s] = 1;
        queue.add(s);

        while(!queue.isEmpty()) {
            int currentFloor = queue.poll();

            if(currentFloor == g) {
                return String.valueOf(visited[currentFloor] - 1);
            }

            for (int value : values) {
                int nextFloor = currentFloor + value;

                if(nextFloor < 1 || nextFloor > f) {
                    continue;
                }
                if(visited[nextFloor] != 0) {
                    continue;
                }

                visited[nextFloor] = visited[currentFloor] + 1;
                queue.add(nextFloor);
            }
        }

        return "use the stairs";
    }
}
