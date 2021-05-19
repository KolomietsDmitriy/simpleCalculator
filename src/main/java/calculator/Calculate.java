package calculator;

import org.slf4j.Logger;

import java.util.*;

import static calculator.TypeElement.*;

public class Calculate {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Calculate.class);

    private static final List<ElementOfString> elementOfStringList = new ArrayList<>();

    static Map<String, Integer> priorityOperation = new HashMap<>();

    static {
        priorityOperation.put("+", 2);
        priorityOperation.put("-", 2);
        priorityOperation.put("*", 1);
        priorityOperation.put("/", 1);
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String s = scanner.nextLine().replace(" ", "");
            String result = calc(s);

            log.info("Ответ: {}", result);
        }
    }

    public static String calc(String inString) {
        parseString(inString.replace(" ", ""));

        return calculateExpression(elementOfStringList);
    }

    public static void parseString(String expression) {
        var iterator = new StringIterator(expression);

        elementOfStringList.clear();

        while (iterator.hasNext()) {
            elementOfStringList.add(iterator.next());
        }
    }

    public static String calculateExpression(List<ElementOfString> elementList) {
        String result = null;
        var index = 0;
        String firstNumber;
        String operator;
        String secondNumber;
        List<ElementOfString> secondElementList = new ArrayList<>();

        while (elementList.size() != 1) {
            if (elementList.get(index).getTypeElement() == OPEN_BRACKET) {
                elementList.remove(index);
                calcExpressionInBracket(elementList, index);
            }
            firstNumber = elementList.get(index).getValue();
            elementList.remove(index);

            if (elementList.size() == 0) {
                //выражение в двойных скобках было
                elementList.add(0, new ElementOfString(firstNumber, NUMBERS));
                return firstNumber;
            }

            operator = elementList.get(index).getValue();
            elementList.remove(index);

            if (elementList.get(index).getTypeElement() == OPEN_BRACKET) {
                elementList.remove(index);
                calcExpressionInBracket(elementList, index);
            }
            secondNumber = elementList.get(index).getValue();
            elementList.remove(index);

            while (elementList.size() > 0 &&
                    (priorityOperation.get(elementList.get(index).getValue()) < priorityOperation.get(operator))) {
//                secondElementList.add(elementList.get(index));
//                elementList.remove(index);
                secondElementList.add(new ElementOfString(secondNumber, NUMBERS));
                secondElementList.add(elementList.get(index));
                elementList.remove(index);
                secondElementList.add(elementList.get(index));
                elementList.remove(index);
                //elementList.add(index, new ElementOfString(calculateExpression(secondElementList), NUMBERS));
                secondNumber = calculateExpression(secondElementList);
                secondElementList.clear();
                //secondNumber = elementList.get(index).getValue();
            }

            result = String.valueOf(switch (operator) {
                case "+" -> Double.parseDouble(firstNumber) + Double.parseDouble(secondNumber);
                case "-" -> Double.parseDouble(firstNumber) - Double.parseDouble(secondNumber);
                case "*" -> Double.parseDouble(firstNumber) * Double.parseDouble(secondNumber);
                case "/" -> Double.parseDouble(firstNumber) / Double.parseDouble(secondNumber);
                default -> throw new IllegalStateException("Unexpected operator: " + operator);
            });

//            elementList.
            elementList.add(0, new ElementOfString(result, NUMBERS));
        }

        return result;
    }

    private static void calcExpressionInBracket(List<ElementOfString> elementList, int index) {
        int countBracket = 1;
        int localIndex = index;
        List<ElementOfString> elementListInBracket = new ArrayList<>();

        while (countBracket != 0) {

            if (elementList.get(localIndex).getTypeElement() == OPEN_BRACKET) {
                countBracket++;
            } else if (elementList.get(localIndex).getTypeElement() == CLOSE_BRACKET) {
                countBracket--;
            }
            if (countBracket != 0) {
                elementListInBracket.add(
                        new ElementOfString(elementList.get(localIndex).getValue(),
                                elementList.get(localIndex).getTypeElement()));
            }

            elementList.remove(localIndex);
        }
        elementList.add(localIndex, new ElementOfString(calculateExpression(elementListInBracket), NUMBERS));
    }


}
