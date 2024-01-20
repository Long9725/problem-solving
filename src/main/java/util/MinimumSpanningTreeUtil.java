package util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class MinimumSpanningTreeUtil<T extends NodeInterface> {
    public int prim(LinkedList<T>[] nodes, T startNode) {
        // 우선순위큐로 안하면 같은 노드지만 가중치가 최소가 아닌 걸 사용할 수도 있음.
        PriorityQueue<T> queue = new PriorityQueue<>(Comparator.comparing(T::getWeight));
        boolean[] inMst = new boolean[nodes.length];
        int amount = 0;

        // 시작 노드는 가중치를 넣어주지 않음.
        queue.add(startNode);

        while (!queue.isEmpty()) {
            T currentNode = queue.poll();

            if (inMst[currentNode.getVertex()]) {
                continue;
            }

            inMst[currentNode.getVertex()] = true;
            amount += currentNode.getWeight();

            // 인접 노드들 전부 추가
            for (T node : nodes[currentNode.getVertex()]) {
                if (!inMst[node.getVertex()]) {
                    queue.add(node);
                }
            }
        }

        return amount;
    }

    public int kruskal(int[][] graph, int v, int e) {
        int amount = 0;
        int[] parent = new int[v + 1];

        // 자기 자신을 루트 노드로 초기화
        for (int i = 1; i <= v; i++) {
            parent[i] = i;
        }

        Arrays.sort(graph, Comparator.comparingInt(left -> left[2]));

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

interface NodeInterface<T> {
    int getVertex();

    long getWeight();
}