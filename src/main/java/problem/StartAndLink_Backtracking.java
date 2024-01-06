package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class StartAndLink_Backtracking {
    static int min = Integer.MAX_VALUE;

    static int _n;

    static int[][] _stats;

    static boolean[] _visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] stats = new int[n][n];

        for (int i = 0; i < n; i++) {
            StringTokenizer stringTokenizer = new StringTokenizer(br.readLine());
            int tokenCount = stringTokenizer.countTokens();
            for (int j = 0; j < tokenCount; j++) {
                stats[i][j] = Integer.parseInt(stringTokenizer.nextToken());
            }
        }

        solution(stats, n);
    }

    static void solution(int[][] stats, int n) {
        _stats = stats;
        _n = n;
        _visited = new boolean[_n];

        findTeam( 0, 0);

        System.out.println(min);
    }

    static void findTeam(int index, int count) {
        if (count == _n / 2) {
            calculateDifference();
            return;
        }
        if (count > _n / 2) {
            return;
        }

        for (int i = index; i < _n; i++) {
            if (!_visited[i]) {
                _visited[i] = true;
                findTeam(i + 1, count + 1);
                _visited[i] = false;
            }
        }
    }

    static void calculateDifference() {
        int startTeam = 0, linkTeam = 0;

        for (int i = 0; i < _n; i++) {
            for (int j = i + 1; j < _n; j++) {
                if (_visited[i] && _visited[j]) {
                    startTeam += _stats[i][j] + _stats[j][i];
                } else if (!_visited[i] && !_visited[j]) {
                    linkTeam += _stats[i][j] + _stats[j][i];
                }
            }
        }

        min = Math.min(min, Math.abs(startTeam - linkTeam));

        if (min == 0) {
            System.out.println(min);
            System.exit(0);
        }
    }
}
