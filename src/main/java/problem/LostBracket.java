package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LostBracket {
    public static void main(String[] args) throws IOException {
        LostBracketSolve solve = new LostBracketSolve();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        String input = st.nextToken();

        Pattern operatorPattern = Pattern.compile("[-+]");
        Pattern decimalPattern = Pattern.compile("\\d+");
        List<String> operators = getMatchingString(input, operatorPattern);
        List<String> decimals = getMatchingString(input, decimalPattern);

        System.out.println(solve.solve(operators, decimals));
    }

    public static List<String> getMatchingString(String text, Pattern pattern) {
        List<String> result = new ArrayList<>();
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String element = matcher.group();
            result.add(element);
        }

        return result;
    }
}

class LostBracketSolve {
    public int solve(List<String> operators, List<String> decimals) {
        int answer = Integer.parseInt(decimals.get(0));
        int sumInBracket = 0;
        boolean existBracket = false;
        // 괄호를 어디다 넣을건지
        // 1. + (30 - 20) == + 30 - 20
        // 2. + (30 + 30) == + 30 + 30
        // 3. - (30 + 30) != - 30 + 30
        // 4. - (30 - 30) != - 30 - 30
        // 3번만 괄호를 씌워주면 됌
        // - 연산자 이후 괄호 추가 => 다음 - 연산자 만나면 괄호 닫기
        // 55 - 50 + 40 + 40 - 50 + 40 + 40 => 55 - (50 + 40 + 40) - (50 + 40 + 40) = -205

        for (int i = 0; i < operators.size(); i++) {
            // 괄호가 있을경우 => -를 만나고 이후 +를 만났을 때
            if (existBracket) {
                sumInBracket -= Integer.parseInt(decimals.get(i + 1));

                // 아직 연산자가 더 남아있고 현재 연산자는 +이면서 다음 연산자가 -이면 괄호를 닫음
                boolean closeBracket = i < operators.size() - 1 && Objects.equals(operators.get(i + 1), "-");

                if (closeBracket) {
                    answer += sumInBracket;
                    sumInBracket = 0;
                    existBracket = false;
                }

                continue;
            }

            // 괄호가 없을경우
            if (Objects.equals(operators.get(i), "+")) {
                answer += Integer.parseInt(decimals.get(i + 1));
            } else {
                // 3. - (30 + 30) != - 30 + 30 에 해당되는지 확인하기
                boolean setBracket = i < operators.size() - 1 && Objects.equals(operators.get(i + 1), "+");

                sumInBracket -= Integer.parseInt(decimals.get(i + 1));

                if (setBracket) {
                    existBracket = true;
                }
            }
        }

        // 마지막에 괄호가 안 닫혀있는 경우의 수를 위해서 더하기
        answer += sumInBracket;

        return answer;
    }
}
