package problem;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PGPracticeSubSequenceSum {
    private final TreeSet<Metadata> metadatas = new TreeSet<>();

    public int[] solution(int[] sequence, int k) {
        int next = -1;

        do {
            int start = next + 1;

            if (start >= sequence.length) {
                break;
            }

            next = calculate(start, sequence, k);
        } while (next < sequence.length);

        return new int[]{metadatas.first().start, metadatas.first().end};
    }

    private int calculate(
            final int start,
            final int[] sequence,
            final int k
    ) {
        int end = start;
        int sum = sequence[start];
        for (int i = start + 1; i < sequence.length; i++) {
            if (sum >= k) {
                break;
            }
            if (metadatas.size() > 0 && metadatas.first().length < end - start) {
                break;
            }
            end = i;
            sum += sequence[end];
        }
        if (sum == k) {
            metadatas.add(new Metadata(start, end, end - start));
        }
        return start + 1;
    }
}

class Metadata implements Comparable<Metadata> {
    final int start;
    final int end;
    final int length;

    public Metadata(int start, int end, int length) {
        this.start = start;
        this.end = end;
        this.length = length;
    }

    @Override
    public int compareTo(Metadata o) {
        final int result = Integer.compare(length, o.length);

        if (result == 0) {
            return Integer.compare(start, o.start);
        }

        return result;
    }
}