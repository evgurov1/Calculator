package org.example;

import java.util.HashMap;
import java.util.Map;

class Calculator {
    private static final Map<String, Integer> ROMAN_NUMERALS = new HashMap<>();

    static {
        ROMAN_NUMERALS.put("I", 1);
        ROMAN_NUMERALS.put("II", 2);
        ROMAN_NUMERALS.put("III", 3);
        ROMAN_NUMERALS.put("IV", 4);
        ROMAN_NUMERALS.put("V", 5);
        ROMAN_NUMERALS.put("VI", 6);
        ROMAN_NUMERALS.put("VII", 7);
        ROMAN_NUMERALS.put("VIII", 8);
        ROMAN_NUMERALS.put("IX", 9);
        ROMAN_NUMERALS.put("X", 10);
    }

    public static String calc(String input) {
        String[] tokens = input.split(" ");

        if (tokens.length != 3) {
            throw new IllegalArgumentException("Некорректный формат ввода. Используйте: число оператор число");
        }

        String firstToken = tokens[0];
        String secondToken = tokens[2];

        boolean firstTokenIsRoman = isRomanNumeral(firstToken);
        boolean secondTokenIsRoman = isRomanNumeral(secondToken);

        if (firstTokenIsRoman != secondTokenIsRoman) {
            throw new IllegalArgumentException("Калькулятор умеет работать только с арабскими или римскими цифрами одновременно");
        }

        try {
            int firstNumber = firstTokenIsRoman ? parseRomanNumeral(firstToken) : parseArabicNumeral(firstToken);
            int secondNumber = firstTokenIsRoman ? parseRomanNumeral(secondToken) : parseArabicNumeral(secondToken);
            char operator = tokens[1].charAt(0);

            int result = switch (operator) {
                case '+' -> firstNumber + secondNumber;
                case '-' -> firstNumber - secondNumber;
                case '*' -> firstNumber * secondNumber;
                case '/' -> firstNumber / secondNumber;
                default -> throw new IllegalArgumentException("Неподдерживаемая операция. Допустимые операторы: +, -, *, /");
            };

            return formatResult(result, input, firstTokenIsRoman);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат чисел");
        }
    }

    private static boolean isRomanNumeral(String token) {
        return ROMAN_NUMERALS.containsKey(token);
    }

    private static int parseRomanNumeral(String token) {
        if (!ROMAN_NUMERALS.containsKey(token)) {
            throw new IllegalArgumentException("Некорректное римское число: " + token);
        }
        return ROMAN_NUMERALS.get(token);
    }

    private static int parseArabicNumeral(String token) {
        int value = Integer.parseInt(token);
        if (value < 1 || value > 10) {
            throw new IllegalArgumentException("Число должно быть от 1 до 10 включительно");
        }
        return value;
    }

    private static String formatResult(int result, String input, boolean isRoman) {
        if (isRoman) {
            if (result <= 0) {
                throw new IllegalArgumentException("Результат с римскими числами не может быть отрицательным или нулем");
            }
            return "Output: " + toRoman(result);
        } else {
            return "Output: " + result;
        }
    }

    private static String toRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Число должно быть в диапазоне от 1 до 3999");
        }

        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder result = new StringBuilder();
        int i = 0;

        while (number > 0) {
            if (number - arabicValues[i] >= 0) {
                result.append(romanSymbols[i]);
                number -= arabicValues[i];
            } else {
                i++;
            }
        }

        return result.toString();
    }
}
