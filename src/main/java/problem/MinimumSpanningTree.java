package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MinimumSpanningTree {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        int[][] graph = new int[e][3];
        MinimumSpanningTreeSolve solve = new MinimumSpanningTreeSolve();

        for (int i = 0; i < graph.length; i++) {
            st = new StringTokenizer(bf.readLine());

            for (int j = 0; j < graph[i].length; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(solve.solve(graph, v, e));
    }
}

class MinimumSpanningTreeSolve {
    public int solve(int[][] graph, int v, int e) {
        System.out.println(kruskal(graph, v, e));

        LinkedList<Node>[] nodes = new LinkedList[v + 1];

        for (int i = 1; i < nodes.length; i++) {
            nodes[i] = new LinkedList<>();
        }

        // 기준 노드 / 연결 노드 / 가중치로 정의, 무방향이므로 양쪽에 추가
        for (int i = 0; i < graph.length; i++) {
            int leftVertex = graph[i][0];
            int rightVertex = graph[i][1];
            int weight = graph[i][2];
            Node leftNode = new Node(leftVertex, weight);
            Node rightNode = new Node(rightVertex, weight);

            // 무방향이므로 양쪽에 추가
            nodes[leftVertex].add(rightNode);
            nodes[rightVertex].add(leftNode);
        }

        return prim(nodes);
    }

    private int prim(LinkedList<Node>[] nodes) {
        // 우선순위큐로 안하면 같은 노드지만 가중치가 최소가 아닌 걸 사용할 수도 있음.
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getWeight));
        boolean[] inMst = new boolean[nodes.length];
        int amount = 0;

        // 시작 노드는 가중치를 넣어주지 않음.
        queue.add(new Node(1, 0));

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (inMst[currentNode.getVertex()]) {
                continue;
            }

            inMst[currentNode.getVertex()] = true;
            amount += currentNode.getWeight();

            // 인접 노드들 전부 추가
            for (Node node : nodes[currentNode.getVertex()]) {
                if (!inMst[node.getVertex()]) {
                    queue.add(node);
                }
            }
        }

        return amount;
    }

    private int kruskal(int[][] graph, int v, int e) {
        int amount = 0;
        int[] parent = new int[v + 1];

        // 자기 자신을 루트 노드로 초기화
        for (int i = 1; i <= v; i++) {
            parent[i] = i;
        }

        Arrays.sort(graph, (left, right) -> left[2] - right[2]);

        for (int[] edge : graph) {
            int src = edge[0];
            int dest = edge[1];
            int weight = edge[2];

            // 서로 루트 노드가 다를 경우
            if (find(parent, src) != find(parent, dest)) {
                amount += weight;

                // 한 개의 집합으로 합치기
                union(parent, src, dest);
            }
        }

        return amount;
    }

    private int find(int[] parent, int vertex) {
        // 내가 루트 노드가 아닌 경우
        if (parent[vertex] != vertex) {
            // 내 윗 노드를 재귀로 탐색해서 루트 노드를 찾아낸 뒤 변경
            parent[vertex] = find(parent, parent[vertex]);
        }
        return parent[vertex];
    }

    private void union(int[] parent, int x, int y) {
        int xRoot = find(parent, x);
        int yRoot = find(parent, y);

        // 서로 루트 노드가 다를 경우 같은 집합으로 병함 => 오름차순 / 내림차순에 따라 할당값 변경
        if (xRoot != yRoot) {
            parent[xRoot] = yRoot;
        }
    }
}

class Node {
    private final int vertex;

    private final int weight;

    public Node(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    public int getVertex() {
        return vertex;
    }

    public int getWeight() {
        return weight;
    }
}