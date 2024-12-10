package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class GenerateCrypto {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int l = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        char[] alphabets = new char[c];

        st = new StringTokenizer(bf.readLine());

        for (int i = 0; i < c; i++) {
            alphabets[i] = st.nextToken().charAt(0);
        }

        PGPCCP2 solution = new PGPCCP2();

        // solution.solve(alphabets, l, c);
    }
}

class Solution {
    private int l;

    private int c;

    private List<String> vowels;

    private List<String> alphabetList;

    private List<String> answers = new ArrayList<>();

    public void solve(char[] alphabets, int l, int c) {
        this.l = l;
        this.c = c;
        vowels = Arrays.asList("a", "e", "i", "o", "u");
        alphabetList = Arrays.stream(String.valueOf(alphabets).split(""))
                .sorted()
                .collect(Collectors.toList());

        for (int i = 0; i < c; i++) {
            boolean[] visited = new boolean[c];

            visited[i] = true;

            dfs(0, visited, alphabetList.get(i));
        }

        answers.stream().sorted().forEachOrdered(System.out::println);

        int[][] test = new int[3][3];
        int[][] newTest = Arrays.stream(test).sorted(Comparator.comparingInt(value -> value[0])).toArray(int[][]::new);
    }

    private void dfs(int count, boolean[] visited, String crypto) {
        if (count == l - 1) {
            // 암호는 서로 다른 L개의 알파벳 소문자들로 구성되며 최소 한 개의 모음(a, e, i, o, u)과 최소 두 개의 자음으로 구성되어 있다고 알려져 있다.
            boolean cryptoCondition = crypto.length() == l && vowels.stream().anyMatch(crypto::contains) && crypto.chars().mapToObj(c -> String.valueOf((char) c)).filter(s -> !vowels.contains(s)).count() > 1;

            if (cryptoCondition) {
                answers.add(crypto);
            }

            return;
        }

        for (int i = 0; i < c; i++) {
            if (!visited[i] && ascCondition(count, i, crypto)) {
                visited[i] = true;
                dfs(count + 1, visited, crypto + alphabetList.get(i));
                visited[i] = false;
            }
        }
    }

    private boolean ascCondition(int count, int index, String crypto) {
        String target = alphabetList.get(index);
        char compare = crypto.charAt(count);

        return compare < target.charAt(0);
    }
}
