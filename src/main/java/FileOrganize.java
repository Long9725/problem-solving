import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileOrganize {
    public static void main(String[] args) throws IOException {
        // BufferedReader로 입력 받기. Scanner보다 빠름; BufferedReader는 날 것 그자체라 빠르고 불편한 대신, Scanner는 내부적인 로직 지원으로 느리고 좀 편함
        // Scanner -> 문자가 입력될 때마다 전달 / BufferedReader -> buffer가 가득차거나 개행문자를 만나면 전달
        // Scanner -> buffer 사이즈 1KB / BufferedReader -> buffer 사이즈 8KB
        // Scanner -> 타입변환 O / BufferedReader -> 타입변환 X
        // Scanner -> Thread safe (내부적으로 동기화) / BufferedReader -> Thread not safe
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bufferedReader.readLine());
        IntStream.range(0, n)
                .mapToObj(i -> {
                    try {
                        return bufferedReader.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(arg -> arg.split("\\.")[1])
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
    }
}
