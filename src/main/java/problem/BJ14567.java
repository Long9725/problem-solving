package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * <a href="https://www.acmicpc.net/problem/14567">백준 14567번 - 선수과목</a>
 */
public class BJ14567 {
    private static int n;

    private static int m;

    private static BJ14567Node[] prerequisites;

    private static List<List<Integer>> adjList = new ArrayList<>();

    private static Queue<BJ14567Node> queue = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] firstLine = bf.readLine().split(" ");
        n = Integer.parseInt(firstLine[0]);
        m = Integer.parseInt(firstLine[1]);
        prerequisites = new BJ14567Node[n];

        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
            prerequisites[i] = new BJ14567Node(i, 0);
        }

        for (int i = 0; i < m; i++) {
            int[] nodes = Arrays.stream(bf.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int src = nodes[0] - 1;
            int dest = nodes[1] - 1;

            adjList.get(src).add(dest);
            prerequisites[dest].inDegree++;
        }

        for (BJ14567Node prerequisite : prerequisites) {
            if (prerequisite.inDegree == 0) {
                queue.add(prerequisite);
            }
        }

        while (!queue.isEmpty()) {
            BJ14567Node current = queue.poll();

            for (Integer next : adjList.get(current.node)) {
                BJ14567Node node = prerequisites[next];

                node.inDegree--;
                node.depth = Math.max(node.depth, current.depth + 1);

                if(node.inDegree == 0) {
                    queue.add(node);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (BJ14567Node node : prerequisites) {
            sb.append(node.depth).append(" ");
        }
        System.out.println(sb.toString().trim());
    }
}

class BJ14567Node {
    final int node;

    int inDegree;

    int depth;

    public BJ14567Node(int node, int inDegree) {
        this.node = node;
        this.inDegree = inDegree;
        this.depth = 1;
    }
}