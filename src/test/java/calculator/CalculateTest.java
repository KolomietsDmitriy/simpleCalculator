package calculator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculateTest {

    @ParameterizedTest(name = "{0} = {1}")
    @CsvSource({
            "1+2,                           3.0,",
            "-7+2,                          -5.0,",
            "1+2*3*4,                       25.0",
            "2+2*8/4,                       6.0",
            "3 - (2 * 2),                   -1.0,",
            "3 - (2 * 2) + (1 + 1),         1.0",
            "3 - (2 * 2 / 2 + 1),           0.0",
            "10 - (2 * (2 / 2 + 1)),        6.0",
            "3 * (4 - 2) * (4 + 1),         30.0",
            "3 * ((4 - 2) + (-20 / 4 + 1)), -6.0",
            "(3 - ((2 * 2))),               -1.0,",

    })
    void calcTest(String expression, String result) {
        Calculate calculator = new Calculate();
        assertEquals(result, calculator.calc(expression));
    }
}