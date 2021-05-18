package calculator;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static calculator.TypeElement.*;

public class Calculate {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Calculate.class);

    private List<ElementOfString> elementOfStringList = new ArrayList<>();

    public static void main(String[] args) {
        var calculator = new Calculate();
        var scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String s = scanner.nextLine().replace(" ", "");

            log.info("Ответ: {}", calculator.calc(s));
        }
    }

    public String calc(String inString) {
        parseString(inString.replace(" ", ""));
        return elementOfStringList.toString();
    }

    public void parseString(String expression) {
        Character symbol;
        var digit = new StringBuilder();
        ElementOfString element = null;
        ElementOfString number;
        boolean toList;
        int indexElement = 0;


        for (int i = 0; i<expression.length(); i++) {
            symbol = expression.charAt(i);

            if (Character.isDigit(symbol) || symbol == '.' || (i == 0 && symbol == '-')) {
                toList = false;
                digit.append(symbol);
            } else {
                toList = true;
                indexElement++;
                element = switch (symbol) {
                    case '+', '-', '*', '/' -> new ElementOfString(indexElement, symbol.toString(), OPERATORS);
                    case '(' -> new ElementOfString(indexElement, symbol.toString(), OPEN_BRACKET);
                    case ')' -> new ElementOfString(indexElement, symbol.toString(), CLOSE_BRACKET);
                    default -> throw new IllegalStateException("Unexpected value: " + symbol);
                };
            }

            if (toList) {
                if (!digit.isEmpty()) {
                    number = new ElementOfString(indexElement - 1, digit.toString(), NUMBERS);
                    elementOfStringList.add(number);
                }
                elementOfStringList.add(element);
                digit.delete(0, digit.length());
            }

        }
    }


}
