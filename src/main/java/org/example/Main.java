package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Input: ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    break;
                }

                String result = Calculator.calc(input);
                System.out.println(result);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                break;  // Завершить программу при возникновении ошибки
            }
        }
        System.out.println("Программа завершена.");
    }
}