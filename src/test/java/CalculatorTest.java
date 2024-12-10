import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    @Test
    void add1() {
        List<Calculator> calculators = Arrays.asList(new Calculator());
        List<Calculator> newCaculators = Arrays.asList(calculators.get(0));
        List<Calculator> newCaculators1 = new ArrayList<>(calculators);

        Calculator calculator = new Calculator();

        int result = calculator.add(1, 1);

        result == 2
    }

    @Test
    void add1() {
        Calculator calculator = new Calculator();

        int result = calculator.add(1, 2);

        result == 3
    }
}