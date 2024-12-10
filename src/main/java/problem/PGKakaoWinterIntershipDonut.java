package problem;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PGKakaoWinterIntershipDonut {
    private final int srcIndex = 0;
    private final int destIndex = 1;
    private final List<GraphNode> nodes = new ArrayList<>();

    public int[] solution(int[][] edges) {
        /**
         * 생성된 정점: 나가는 간선이 2개 이상 존재하고, 들어오는 간선이 존재하지 않습니다.
         * 막대 모양 그래프: 생성된 정점과 연결된 간선을 제외했을 때, 나가는 간선이 없는 정점이 하나 존재합니다. 나머지 정점들은 들어오는 정점과 나가는 간선이 모두 하나씩 존재합니다.
         * 도넛 모양 그래프: 생성된 정점과 연결된 간선을 제외했을 때, 모든 정점이 나가는 간선과 들어오는 간선이 하나씩 존재합니다.
         * 8자 모양 그래프: 생성도니 정점과 연결된 간선을 제외했을 때, 1개의 정점을 제외하면 나가는 간선과 들어오는 정점이 하나씩 존재합니다. 1개의 정점은 나가는 간선 2개와, 들어오는 간선 2개가 존재합니다.
         */

        final int[] answer = new int[4];
        final int max = getMax(edges);
        for (int i = 0; i < max; i++) {
            nodes.add(new GraphNode(i + 1));
        }
        for (int[] edge : edges) {
            final int src = edge[srcIndex] - 1;
            final int dest = edge[destIndex] - 1;

            nodes.get(src).out++;
            nodes.get(dest).in++;
        }
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).in == 0 && nodes.get(i).out >= 2) {
                answer[0] = nodes.get(i).number;
            } else if (nodes.get(i).in > 0 && nodes.get(i).out == 0) {
                answer[2]++;
            } else if (nodes.get(i).in >= 2 && nodes.get(i).out >= 2) {
                answer[3]++;
            }
        }
        answer[1] = nodes.get(answer[0] - 1).out - (answer[2] + answer[3]);

        return answer;
    }

    private int getMax(int[][] edges) {
        int max = 0;

        for (int[] edge : edges) {
            max = Math.max(max, Math.max(edge[0], edge[1]));
        }
        return max;
    }
}

class GraphNode {
    final int number;
    int in;
    int out;

    public GraphNode(final int number) {
        this.number = number;
        this.in = 0;
        this.out = 0;
    }
}