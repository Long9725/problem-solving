package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://www.acmicpc.net/problem/1302">백준 1302번 - 베스트셀러</a>
 */
public class BJ1302 {
    private static Map<String, Integer> bookRanking = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bf.readLine());

        for (int i = 0; i < n; i++) {
            String title = bf.readLine();
            if (bookRanking.containsKey(title)) {
                bookRanking.put(title, bookRanking.get(title) + 1);
            } else {
                bookRanking.put(title, 1);
            }
        }

        int max = bookRanking.values().stream()
                .max(Integer::compareTo)
                .get();
        String top = bookRanking.keySet().stream()
                .filter(key -> bookRanking.get(key) == max)
                .min(String::compareTo)
                .get();

        System.out.println(top);
    }
}
