package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BJ1011 {
    public static void main(String[] args) throws IOException {
        /**
         * 현재 위치 x / 목표 위치 y - 정수, 0 <= x < y < 2^31
         *
         * 입력값이 굉장히 큰 문제.
         *
         * k -> k-1 / k / k+1
         * 도착하기 직전에는 꼭 1광년 남아야함.
         * 거리 움직이는거 표   횟수         최대값
         * 1   1            1           1
         * 2   1 1          2           1
         * 3   1 1 1        3           2
         * 4   1 2 1        3           2
         * 5   1 2 1 1      4           2
         * 6   1 2 2 1      4           2
         * 7   1 2 2 1 1    5           2
         * 8   1 2 2 2 1    5           2
         * 9   1 2 3 2 1    5           3
         * 10  1 2 3 2 1 1  6           3
         * 11  1 2 3 2 2 1  6           3
         * 12  1 2 3 3 2 1  6           3
         * 13  1 2 3 3 2 1 1 7          3
         * 14  1 2 3 3 2 2 1 7          3
         * 15  1 2 3 3 3 2 1 7          3
         * 16  1 2 3 4 3 2 1 7          4
         * 17  1 2 3 4 3 2 1 1 8        4
         * 횟수  거리  최대값
         * 1    1    1
         * 2    2    1
         * 3    4    2
         * 4    6    2
         * 5    9    3
         * 6    12   3
         * 7    16   4
         * count =>
         *  1. max^2 => 2 * max - 1
         *  2. max^2 < distance <= max(max + 1) => 2 * max
         *  3. 2 * max + 1
         * max = floor(sqrt(distance))
         *
         */
        final BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        final int t = Integer.parseInt(bf.readLine());

        for(int i = 0; i < t; i++) {
            final String[] inputs = bf.readLine().split(" ");
            final int x = Integer.parseInt(inputs[0]);
            final int y = Integer.parseInt(inputs[1]);
            System.out.println(move(x, y));
        }
    }

    public static int move(int x, int y) {
        final int distance = y - x;
        final double distanceSquare = Math.sqrt(distance);
        final double max = Math.floor(distanceSquare);

        if(max == distanceSquare) {
            return (int) (2 * max - 1);
        }
        if(distance <= max * (max + 1)) {
            return (int) (2 * max);
        }
        return (int) (2 * max + 1);
    }
}
