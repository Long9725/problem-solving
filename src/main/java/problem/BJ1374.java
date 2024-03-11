package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BJ1374 {
    // 메모리 초과
    // private static List<int[]> roomEndTimes = new ArrayList<>();

    private static PriorityQueue<Integer> roomEndTimes = new PriorityQueue<>();

    private static int max = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bf.readLine());
        int[][] arr = new int[n][3];
        // 이건 바로 메모리 초과
        // int[][] dp = new int[100001][100001];

        for (int i = 0; i < n; i++) {
            String[] inputs = bf.readLine().split(" ");

            for (int j = 0; j < inputs.length; j++) {
                arr[i][j] = Integer.parseInt(inputs[j]);
            }
        }

        // dp로 하기엔 int[100001][100001]이 400KB * 400KB 라서 메모리를 초과한다.
        // 시간제한이 2초라 최대로 허용되는 연산은 O(NlogN)이라 봐야한다.
        // 이러면 완탐도 안되고 dp도 안된다. 하지만 최솟값이다 보니 dp만큼 적절한게 없다 => 그리디를 써보자.
        // 강의 시작 시간 순으로 정렬하기
        // 강의실 개수를 계속 저장해봣는데 메모리 초과가 뜬다. 그럼 강의실 개수를 객체로 저장하는게 아니라, primitive로 저장?
        // 그럼 음... 어느 순간 최대인거 저장하기? dp아녀 이럼?

        // 강의 시작 시간으로 정렬하기
        Arrays.sort(arr, Comparator.comparingInt(a -> a[1]));

        // 초깃값 세팅;
        roomEndTimes.add(arr[0][2]);

        for (int i = 1; i < arr.length; i++) {
            // 시작시간이 종료시간과 같거나 더 큰지 확인
            while (!roomEndTimes.isEmpty() && roomEndTimes.peek() <= arr[i][1]) {
                roomEndTimes.poll();
            }
            roomEndTimes.add(arr[i][2]);
            max = Math.max(max, roomEndTimes.size());
        }

        System.out.println(max);
    }
}
