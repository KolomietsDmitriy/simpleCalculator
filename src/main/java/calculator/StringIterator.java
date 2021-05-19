package calculator;

import java.util.Iterator;

import static calculator.TypeElement.*;

public class StringIterator implements Iterator<ElementOfString> {

    private final String expression;
    private int index;

    public StringIterator(String expression) {
        this.expression = expression;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        return (expression != null && index < expression.length());
    }

    @Override
    public ElementOfString next() {
        ElementOfString currentElement;
        Character symbol = expression.charAt(index);
        var digit = new StringBuilder();

        while ((Character.isDigit(symbol) ||
                symbol == '.' ||
                (index == 0 && symbol == '-') ||
                (index > 0 && expression.charAt(index - 1) == '(' && symbol == '-'))
                && index < expression.length()) {
            digit.append(symbol);
            index++;
            if (index < expression.length()) {
                symbol = expression.charAt(index);
            }
        }

        if (!digit.isEmpty()) {
            currentElement = new ElementOfString(digit.toString(), NUMBERS);
        } else {
            index++;
            currentElement = switch (symbol) {
                case '+', '-', '*', '/' -> new ElementOfString(symbol.toString(), OPERATORS);
                case '(' -> new ElementOfString(symbol.toString(), OPEN_BRACKET);
                case ')' -> new ElementOfString(symbol.toString(), CLOSE_BRACKET);
                default -> throw new IllegalStateException("Unexpected value: " + symbol);
            };

        }

        return currentElement;
    }
}
