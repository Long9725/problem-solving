package problem;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class PGPCCP2 {
    public int solution(int[] diffs, int[] times, long limit) {
        // 퍼즐 리스트 생성
        final Puzzles puzzles = new Puzzles(IntStream.range(0, diffs.length)
                .mapToObj(i -> new Puzzle(diffs[i], times[i]))
                .collect(Collectors.toList()));

        // 최소 숙련도 계산
        return puzzles.findMinimumLevel(limit);
    }
}

// 퍼즐을 나타내는 클래스
class Puzzle {
    int difficulty;
    int time;

    public Puzzle(int difficulty, int time) {
        this.difficulty = difficulty;
        this.time = time;
    }

    public long getSolveTime(
            final long previousTime,
            final int level
    ) {
        if (difficulty <= level) {
            return time;
        }
        final long mistakes = difficulty - level;
        final long timePerMistake = (long) time + previousTime;
        return mistakes * timePerMistake + time;
    }
}

class Puzzles {
    private final List<Puzzle> puzzles;

    public Puzzles(final List<Puzzle> puzzles) {
        this.puzzles = puzzles;
    }

    // 최소 숙련도를 이진 탐색으로 찾는 메서드
    public int findMinimumLevel(final long limit) {
        int left = 1;
        int right = getMaxDifficulty(puzzles) + 1000000; // 충분히 큰 값으로 설정

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canSolveAllPuzzles(mid, limit)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    // 주어진 숙련도로 모든 퍼즐을 해결할 수 있는지 확인하는 메서드
    private boolean canSolveAllPuzzles(
            final int level,
            final long limit
    ) {
        // 첫 번째 퍼즐 처리
        final long baseTime = puzzles.get(0).time;

        if (baseTime > limit) {
            return false;
        }

        return limit >= baseTime + IntStream.range(1, puzzles.size())
                .mapToLong(i -> {
                    final Puzzle previousPuzzle = puzzles.get(i - 1);
                    final Puzzle currentPuzzle = puzzles.get(i);
                    return currentPuzzle.getSolveTime(previousPuzzle.time, level);
                })
                .sum();
    }

    // 퍼즐들 중 최대 난이도를 반환하는 메서드
    private int getMaxDifficulty(final List<Puzzle> puzzles) {
        return puzzles.stream().max(Comparator.comparingInt(puzzle -> puzzle.difficulty))
                .map(puzzle -> puzzle.difficulty)
                .orElse(0);
    }
}