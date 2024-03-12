package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * <a href="https://www.acmicpc.net/problem/9935">백준 9935번 - 문자열 폭발</a>
 */
public class BJ9935 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        char[] origin = bf.readLine().toCharArray();
        char[] boom = bf.readLine().toCharArray();
        Stack<Character> stack = new Stack<>();
        /**
         * 폭발 문자열이 폭발하면 그 문자는 문자열에서 사라지며, 남은 문자열은 합쳐지게 된다.
         *
         * 폭발은 다음과 같은 과정으로 진행된다.
         *
         * 1. 문자열이 폭발 문자열을 포함하고 있는 경우에, 모든 폭발 문자열이 폭발하게 된다. 남은 문자열을 순서대로 이어 붙여 새로운 문자열을 만든다.
         * 2. 새로 생긴 문자열에 폭발 문자열이 포함되어 있을 수도 있다.
         * 3. 폭발은 폭발 문자열이 문자열에 없을 때까지 계속된다.
         *
         * 모든 폭발이 끝난 후에 어떤 문자열이 남는지 구해보려고 한다. 남아있는 문자가 없는 경우가 있다. 이때는 "FRULA"를 출력한다.
         * */

        for (char c : origin) {
            stack.push(c);

            if(stack.size() >= boom.length) {
                boolean flag = true;

                for(int j = 0; j <boom.length; j++) {
                    char originChar = stack.get(stack.size() + j - boom.length);
                    char boomChar = boom[j];

                    if(originChar != boomChar) {
                        flag = false;
                        break;
                    }
                }

                if(flag) {
                    for(int j = 0 ; j < boom.length; j++) {
                        stack.pop();
                    }
                }
            }
        }

        StringBuffer result = new StringBuffer();

        for (Character character : stack) {
            result.append(character);
        }
        if (result.length() == 0) {
            System.out.println("FRULA");
            return;
        }
        System.out.println(result);
    }
}
