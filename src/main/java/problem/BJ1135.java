package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BJ1135 {
    // 상사 입장에서 부하직원 트리
    static List<Integer>[] tree;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] superiors = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        tree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        // 상사 입장에서 부하직원 세팅
        // 민식이는 제외
        for (int i = 1; i < n; i++) {
            tree[superiors[i]].add(i);
        }

        System.out.println(dfs(0));
    }

    static int dfs(int superior) {
        if (tree[superior].isEmpty()) return 0;

        // 부하 직원들에게 모두 전파하는데 필요한 시간 배열
        List<Integer> times = new ArrayList<>();
        int maxTime = 0;

        // dfs로 얼마나 전파가 오래 걸리는지 파악
        for (Integer employee : tree[superior]) {
            times.add(dfs(employee));
        }

        // 시간이 가장 오래 걸리는 순으로 정렬
        times.sort(Collections.reverseOrder());

        // 가장 오래 걸리는 시간 파악
        for (int i = 0; i < times.size(); i++) {
            maxTime = Math.max(maxTime, times.get(i) + i + 1);
        }

        return maxTime;
    }
}
