package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class InequalitySign {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        char[] inequalitySigns = new char[n];

        StringTokenizer nextSt = new StringTokenizer(bf.readLine());

        for (int i = 0; i < n; i++) {
            inequalitySigns[i] = nextSt.nextToken().charAt(0);
        }

        InequalitySignSolution solution = new InequalitySignSolution();

        solution.solve(inequalitySigns);
    }
}

class InequalitySignSolution {
    List<List<Integer>> allCount = new ArrayList<>();

    char[] inequalitySigns;

    boolean[] visited;

    int MAX_LENGTH = 10;

    int MAX_COUNT;

    public void solve(char[] inequalitySigns) {
        this.inequalitySigns = inequalitySigns;
        visited = new boolean[MAX_LENGTH];
        MAX_COUNT = inequalitySigns.length;

        for (int i = 0; i < MAX_LENGTH; i++) {
            boolean[] visited = new boolean[MAX_LENGTH];
            visited[i] = true;
            dfs(i, 0, new ArrayList<>(), visited);
        }

        List<Long> nums = allCount.stream()
                .map(integers -> Long.parseLong(integers.stream()
                        .map(String::valueOf)
                        .reduce((left, right) -> left + right)
                        .orElseThrow(RuntimeException::new))
                )
                .collect(Collectors.toList());
        String maxString = nums.stream().max(Long::compare).orElseThrow(RuntimeException::new).toString();
        String minString = nums.stream().min(Long::compare).orElseThrow(RuntimeException::new).toString();

        System.out.println("0000000000".substring(0, MAX_COUNT + 1 - maxString.length()) + maxString);
        System.out.println("0000000000".substring(0, MAX_COUNT + 1 - minString.length()) + minString);
    }

    public void dfs(int num, int count, List<Integer> characters, boolean[] visited) {
        if (count == MAX_COUNT) {
            List<Integer> newList = new ArrayList<>(characters);

            newList.add(num);

            allCount.add(newList);
            return;
        }

        for (int i = 0; i < MAX_LENGTH; i++) {
            if (!visited[i] && condition(num, i, count)) {
                List<Integer> newList = new ArrayList<>(characters);

                newList.add(num);

                visited[i] = true;
                dfs(i, count + 1, newList, visited);
                visited[i] = false;
            }
        }
    }

    public boolean condition(int num, int target, int index) {
        if (num == target) {
            return false;
        }

        char inequalitySign = inequalitySigns[index];

        if (inequalitySign == '>') {
            return num > target;
        }
        return num < target;
    }
}
