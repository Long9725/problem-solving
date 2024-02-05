package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class DevelopGame {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int[] results = new int[n];
        DevelopGameBuilding[] buildings = new DevelopGameBuilding[n];
        List<List<Integer>> adjacencyGraphForTopologicalSorting = new ArrayList<>();
        List<List<Integer>> adjacencyGraphForResults = new ArrayList<>();
        Queue<DevelopGameBuilding> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            adjacencyGraphForTopologicalSorting.add(new ArrayList<>());
            adjacencyGraphForResults.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());
            int count = st.countTokens();

            for (int j = 0; j < count - 1; j++) {
                if (j == 0) {
                    int builtTime = Integer.parseInt(st.nextToken());

                    // 건물 짓는 시간
                    buildings[i] = new DevelopGameBuilding(i, builtTime, 0);
                } else {
                    int node = Integer.parseInt(st.nextToken()) - 1;

                    // 인접그래프
                    adjacencyGraphForTopologicalSorting.get(node).add(i);
                    adjacencyGraphForResults.get(i).add(node);

                    // 진입차수 증가
                    buildings[i].addEntryLevel();
                }
            }
        }

        // List에 노드 객체를 넣고, 진입 차수를 기준으로 오름차순 정렬? => 어차피 1부터 시작할건데. 그냥 인접그래프에 있는거 넣
        // 연결된 간선을 알아야함. 간선을 찾아내서 진입차수 감소 시켜야함록

        // 진입차수가 0인것부터 시작, 처음 시간 세팅
        for (int i = 0; i < buildings.length; i++) {
            if(buildings[i].getEntryLevel() == 0) {
                queue.add(buildings[i]);
                results[i] = buildings[i].builtTime;
            }
        }

        // 위상 정렬 시작
        while (!queue.isEmpty()) {
            DevelopGameBuilding current = queue.poll();
            List<Integer> currentArr = adjacencyGraphForTopologicalSorting.get(current.node);

            for (int i = 0; i < currentArr.size(); i++) {
                // 인접한 노드들
                int index = currentArr.get(i);
                DevelopGameBuilding next = buildings[index];

                // 다음꺼 진입차수 감소
                next.decreaseEntryLevel();

                // 진입차수가 0이면 큐에 추가
                if (next.getEntryLevel() == 0) {
                    List<Integer> nextResultArr = adjacencyGraphForResults.get(next.node);
                    int max = Integer.MIN_VALUE;

                    // next까지 지어야하는 건물 경로 중, 시간이 제일 오래걸리는 것 탐색
                    for (int j = 0; j < nextResultArr.size(); j++) {
                        int resultIndex = nextResultArr.get(j);

                        if (max < results[resultIndex]) {
                            max = results[resultIndex];
                        }
                    }

                    // 최댓값과 자기 자신 짓는데 걸리는 시간 추가
                    results[next.node] = max + next.builtTime;

                    // 다음 위상 정렬을 위해 추가
                    queue.add(next);
                }
            }
        }

        // 결과 출력
        for (int i = 0; i < results.length; i++) {
            System.out.println(results[i]);
        }
    }
}

class DevelopGameBuilding {
    final int node;
    final int builtTime;

    private int entryLevel;

    public DevelopGameBuilding(int node, int builtTime, int entryLevel) {
        this.node = node;
        this.builtTime = builtTime;
        this.entryLevel = entryLevel;
    }

    public void addEntryLevel() {
        this.entryLevel++;
    }

    public void decreaseEntryLevel() {
        if (this.entryLevel > 0) {
            this.entryLevel--;
        }
    }

    public int getEntryLevel() {
        return entryLevel;
    }

    @Override
    public String toString() {
        return "DevelopGameBuilding{" +
                "node=" + node +
                ", builtTime=" + builtTime +
                ", entryLevel=" + entryLevel +
                '}';
    }
}
