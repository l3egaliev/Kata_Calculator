import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение (например, 2 + 3):");
        String input = scanner.nextLine();
        scanner.close();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    static String calc(String input) {
        // Удаляем пробелы, если есть.
        String[] parts = input.replaceAll("\\s+", "").split("");

        String operand1 = "";
        String operator = "";
        String operand2 = "";
        if (parts.length == 4){
            String c1 = parts[0];
            String c2 = parts[1];
            operand1 += c1+c2;
            operand2 += parts[3];
            operator += parts[2];
        }else if(parts.length == 3){
            operand1 += parts[0];
            operator += parts[1];
            operand2 += parts[2];
        }else
            throw new IllegalArgumentException("Неправильный формат выражения");


        boolean isRoman = isRomanNumber(operand1) && isRomanNumber(operand2);

        int num1 = isRoman ? romanToDecimal(operand1) : Integer.parseInt(operand1);
        int num2 = isRoman ? romanToDecimal(operand2) : Integer.parseInt(operand2);

        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10)
            throw new IllegalArgumentException("Числа должны быть от 1 до 10 включительно");

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Неподдерживаемая операция");
        }

        if (isRoman) {
            if (result < 1)
                throw new IllegalArgumentException("Результат не может быть меньше 1");
            return decimalToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    static boolean isRomanNumber(String s) {
        return s.matches("[IVXLCDM]+");
    }

    static int romanToDecimal(String s) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int result = 0;
        int prevValue = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            int curValue = romanMap.get(s.charAt(i));
            if (curValue < prevValue) {
                result -= curValue;
            } else {
                result += curValue;
            }
            prevValue = curValue;
        }

        return result;
    }

    static String decimalToRoman(int num) {
        String[] romanSymbols = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
        int[] values = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
        StringBuilder roman = new StringBuilder();

        int i = values.length - 1;
        while (num > 0) {
            if (num - values[i] >= 0) {
                roman.append(romanSymbols[i]);
                num -= values[i];
            } else {
                i--;
            }
        }
        return roman.toString();
    }
}
