package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// 메모리 128MB에서는 메모리 오버플로우 발생
public class StandInOneLine {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int[] heightOrders = new int[n];

        st = new StringTokenizer(bf.readLine());

        for (int i = 0; i < n; i++) {
            heightOrders[i] = Integer.parseInt(st.nextToken());
        }

        StandInOneLineSolve solve = new StandInOneLineSolve();
        String result = Arrays.stream(solve.solve(heightOrders, n)).mapToObj(i -> String.valueOf(i + 1)).reduce((left, right) -> left + " " + right).get();
        System.out.println(result);
    }
}

class StandInOneLineSolve {
    public int[] solve(int[] heightOrders, int n) {
        List<Integer> answer = new ArrayList<>();

        for (int i = n-1; i >= 0; i--) {
            answer.add(heightOrders[i], i);
        }

        return answer.stream().mapToInt(Integer::intValue).toArray();
    }
}
