package TheCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class RealCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要计算的表达式");
        String expression = sc.nextLine();
        double result = ThisCalculator(expression);
        if (result > Integer.MAX_VALUE) {
            throw new RuntimeException("表达式计算量过大！");
        } else {
            System.out.println(result);
        }
    }

    public static double ThisCalculator(String expression) {
        double result;
        Stack<BigDecimal> stacknum = new Stack<>();
        Stack<String> stacknews = new Stack<>();
        StringBuilder temp = new StringBuilder();
        if (expression == null) {
            throw new RuntimeException("表达式空白");
        }
        for (int i = 0; i < expression.length(); i++) {
            String test = String.valueOf(expression.charAt(i));
            if (!test.matches("[-+*/1234567890().= ]")) {
                throw new RuntimeException("表达式中含有非法字符！");
            }
        }
        for (int i = 0; i < expression.length(); i++) {
            char s = expression.charAt(i);
            if (s != ' ') {
                if ((s >= '0' && s <= '9') || s == '.') {
                    temp.append(s);
                } else {
                    if (temp.length() > 0) {
                        stacknum.push(new BigDecimal(temp.toString()));
                        temp.delete(0, temp.length());
                    }

                    String sign = String.valueOf(s);
                    if (stacknews.isEmpty()) {
                        stacknews.push(sign);
                    } else {
                        if (sign.equals("(")) {
                            stacknews.push(sign);
                        } else if (sign.equals(")")) {
                            totalcount(stacknum, stacknews, false);
                        } else if (sign.equals("=")) {
                            totalcount(stacknum, stacknews, true);
                            return stacknum.pop().doubleValue();
                        } else {
                            partcount(stacknum, stacknews, sign);
                        }

                    }
                }
            }
        }

        if (temp.length() > 0) {
            stacknum.push(new BigDecimal(temp.toString()));
            temp.delete(0, temp.length());
        }
        totalcount(stacknum, stacknews, true);
        return stacknum.pop().doubleValue();
    }


    public static void totalcount(Stack<BigDecimal> stacknum, Stack<String> stacknews, boolean checking) {
        String temp = stacknews.pop();
        BigDecimal num1 = stacknum.pop();
        BigDecimal num2 = stacknum.pop();
        BigDecimal bound = normalcount(temp, num2, num1);
        stacknum.push(bound);
        if (checking) {
            if (!stacknews.isEmpty()) {
                totalcount(stacknum, stacknews, checking);
            }
        } else {
            if (stacknews.peek().equals("(")) {
                stacknews.pop();
            } else {
                totalcount(stacknum, stacknews, checking);
            }
        }
    }

    public static void partcount(Stack<BigDecimal> stacknum, Stack<String> stacknews, String sign) {
        int sp = judgement(sign, stacknews.peek());
        if (sp == 0 || sp == 1) {
            BigDecimal b1 = stacknum.pop();
            BigDecimal b2 = stacknum.pop();
            String temp = stacknews.pop();
            BigDecimal bound = normalcount(temp, b2, b1);
            stacknum.push(bound);
            if (stacknews.isEmpty()) {
                stacknews.push(sign);
            } else {
                partcount(stacknum, stacknews, sign);
            }
        } else {
            stacknews.push(sign);
        }
    }

    public static BigDecimal normalcount(String sign, BigDecimal data1, BigDecimal data2) {
        BigDecimal result = new BigDecimal(0);
        switch (sign) {
            case "+":
                result = data1.add(data2);
                break;
            case "-":
                result = data1.subtract(data2);
                break;
            case "*":
                result = data1.multiply(data2);
                break;
            case "/":
                if (data2.equals(BigDecimal.ZERO)) {
                    throw new RuntimeException("分母不能为零!");
                }
                result = data1.divide(data2, 10, RoundingMode.HALF_UP);
            default:
                break;
        }
        return result;
    }

    private static final Map<String, Integer> Map = new HashMap<String, Integer>() {
        private static final long serialVersionUID = 6968472606692771458L;

        {
            put("(", 100);
            put("+", 0);
            put("-", 0);
            put("*", 1);
            put("/", 1);
            put(")", 28);
            put("=", 48);
        }
    };

    public static int judgement(String t1, String t2) {
        int priority = Map.get(t2) - Map.get(t1);
        return priority;
    }

}
