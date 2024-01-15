package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Rainy {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer firstSt = new StringTokenizer(bf.readLine());
        StringTokenizer lastSt = new StringTokenizer(bf.readLine());
        int h = Integer.parseInt(firstSt.nextToken());
        int w = Integer.parseInt(firstSt.nextToken());
        int[] blocks = new int[w];

        for (int i = 0; i < w; i++) {
            blocks[i] = Integer.parseInt(lastSt.nextToken());
        }

        RainySolution solution = new RainySolution();

        solution.solve(blocks, h, w);
    }
}

class RainySolution {
    public void solve(int[] blocks, int h, int w) {
        int amount = 0;

        // 첫 블록이랑 마지막 블록은 빗물이 고일 수 없다.
        // 현재 기준 왼쪽 높은거, 오른쪽 높은거 찾기
        // 만약 둘 중 하나라도 0이라면 빗물이 고일 수 없으니 제거
        // 왼쪽, 오른쪽 중에 더 낮은거 선택
        // 현재값이 MAX 높이보다 낮다면 빗물 양 계산
        for (int i = 1; i < blocks.length - 1; i++) {
            int leftMax = Integer.MIN_VALUE;
            int rightMax = Integer.MIN_VALUE;
            int current = blocks[i];

            for (int j = 0; j < i; j++) {
                if(leftMax < blocks[j]) {
                    leftMax = blocks[j];
                }
            }

            for (int j = i + 1; j < blocks.length; j++) {
                if(rightMax < blocks[j]) {
                    rightMax = blocks[j];
                }
            }

            if(leftMax == 0 || rightMax == 0) {
                continue;
            }

            if (leftMax < rightMax) {
                if(leftMax > current) {
                    amount += leftMax - current;
                }
            } else {
                if(rightMax > current) {
                    amount += rightMax - current;
                }
            }
        }

        System.out.println(amount);
    }
}