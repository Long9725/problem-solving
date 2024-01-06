package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StartAndLink_Combination {
    static int min = Integer.MAX_VALUE;

    static int _n;

    static int[][] _stats;

    static CombinationGenerator<Integer> generator;

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
        generator = new CombinationGenerator<>();

        combination();

        System.out.println(min);
    }

    static void combination() {
        List<List<Integer>> combinations = generator.generate(IntStream.range(0, _n).boxed().collect(Collectors.toList()), _n / 2);
        Iterator<Integer> resultIterator = IntStream.range(0, combinations.size() / 2)
                .mapToObj(i -> {
                    List<Integer> startTeamPlayer = combinations.get(i);
                    List<Integer> linkTeamPlayer = IntStream.range(0, _n).boxed().filter(playerIndex -> !startTeamPlayer.contains(playerIndex)).collect(Collectors.toList());
                    return Math.abs(calculateStatSum(startTeamPlayer) - calculateStatSum(linkTeamPlayer));
                })
                .iterator();

        while (resultIterator.hasNext()) {
            int result = resultIterator.next();
            if (min > result) {
                min = result;
            }
        }
    }

    static int calculateStatSum(List<Integer> players) {
        List<List<Integer>> playerCombinations = generator.generate(players, 2);
        return playerCombinations.stream()
                .map(playerCombination -> {
                    int firstPlayer = playerCombination.get(0);
                    int secondPlayer = playerCombination.get(1);
                    return _stats[firstPlayer][secondPlayer] + _stats[secondPlayer][firstPlayer];
                })
                .reduce(Integer::sum)
                .orElseThrow(RuntimeException::new);
    }
}

class CombinationGenerator<T> {

    public List<List<T>> generate(List<T> list, int r) {
        assert (list.size() >= r);
        List<List<T>> combinations = new ArrayList<>();
        backtrack(combinations, new ArrayList<>(), list, 0, r);
        return combinations;
    }

    private void backtrack(List<List<T>> combinations, List<T> temp, List<T> list, int start, int remain) {
        if (remain == 0) {
            combinations.add(new ArrayList<>(temp));
            return;
        }

        for (int i = start; i < list.size(); i++) {
            temp.add(list.get(i));
            backtrack(combinations, temp, list, i + 1, remain - 1);
            temp.remove(temp.size() - 1);
        }
    }
}
