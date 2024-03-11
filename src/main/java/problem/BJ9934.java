package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <a href="https://www.acmicpc.net/problem/9934">백준 9934번 - 완전 이진 트리</a>
 */
public class BJ9934 {
    private static List<Integer>[] completeBinaryTree;

    private static int k;

    private static int[] numbers;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        k = Integer.parseInt(bf.readLine());
        numbers = Arrays.stream(bf.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        completeBinaryTree = new ArrayList[k];

        for (int i = 0; i < k; i++) {
            completeBinaryTree[i] = new ArrayList<>();
        }

        /**
         * 이진 탐색을 사용하돼 left 먼저 뎁스 배열에 추가
         * */

        createCompleteBinaryTree(0, numbers);

        for (List<Integer> integers : completeBinaryTree) {
            System.out.println(integers.stream().map(String::valueOf).reduce((left, right) -> left + " " + right).get());
        }
    }

    private static void createCompleteBinaryTree(int depth, int[] arr) {
        if (depth == k) {
            return;
        }

        int mid = (int) (Math.pow(2, k - depth) - 1) / 2;

        completeBinaryTree[depth].add(arr[mid]);

        createCompleteBinaryTree(depth + 1, Arrays.copyOfRange(arr, 0, mid));
        createCompleteBinaryTree(depth + 1, Arrays.copyOfRange(arr, mid + 1, arr.length));
    }
}
