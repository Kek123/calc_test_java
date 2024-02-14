import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите арифметическое выражение: ");
        String input = scanner.nextLine();

        try {
            String result = Main.calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) throws Exception {

        String[] parts = input.trim().split(" ");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Некорректный формат ввода");
        }

        int chislo1, chislo2;
        if ((parts[0].matches(".*[a-zA-Z].*"))&&(parts[2].matches(".*[a-zA-Z].*"))) {
            chislo1 = convertToDecimal(parts[0]);
            chislo2 = convertToDecimal(parts[2]);
        } else {
            try {
                chislo1 = Integer.parseInt(parts[0]);
                chislo2 = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Некорректные числа");
            }
        }

        if (chislo1 < 1 || chislo1 > 10 || chislo2 < 1 || chislo2 > 10) {
            throw new IllegalArgumentException("Числа должны быть от 1 до 10");
        }

        int result;
        switch (parts[1]) {
            case "+":
                result = chislo1 + chislo2;
                break;
            case "-":
                result = chislo1 - chislo2;
                break;
            case "*":
                result = chislo1 * chislo2;
                break;
            case "/":
                result = chislo1 / chislo2;
                break;
            default:
                throw new IllegalArgumentException("Неподдерживаемая арифметическая операция: " + parts[1]);
        }

        if (input.matches(".*[a-zA-Z].*")) {
            if (result <= 0) {
                throw new Exception("Результат меньше единицы, невозможно представить римскими цифрами");
            }
            return toRomanNumeral(result);
        } else {
            return Integer.toString(result);
        }
    }

    private static String toRomanNumeral(int number) {
        StringBuilder romanNumeral = new StringBuilder();
        int[] decimal = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        for (int i = 0; i < decimal.length; i++) {
            while (number >= decimal[i]) {
                romanNumeral.append(romanSymbols[i]);
                number -= decimal[i];
            }
        }
        return romanNumeral.toString();
    }

    public static int convertToDecimal(String roman) {
        HashMap<Character, Integer> romanValues = new HashMap<>();
        romanValues.put('I', 1);
        romanValues.put('V', 5);
        romanValues.put('X', 10);
        romanValues.put('L', 50);
        romanValues.put('C', 100);
        romanValues.put('D', 500);
        romanValues.put('M', 1000);

        int decimal = 0;

        for (int i = 0; i < roman.length(); i++) {
            if (i > 0 && romanValues.get(roman.charAt(i)) > romanValues.get(roman.charAt(i - 1))) {
                decimal += romanValues.get(roman.charAt(i)) - 2 * romanValues.get(roman.charAt(i - 1));
            } else {
                decimal += romanValues.get(roman.charAt(i));
            }
        }

        return decimal;
    }
}