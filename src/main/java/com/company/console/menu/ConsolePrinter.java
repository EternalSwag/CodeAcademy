package com.company.console.menu;

public class ConsolePrinter {

    public static void printSeparator(char c, int i) {
        for (int j = 0; j < i; j++) {
            System.out.print(c);
        }
        System.out.println("");
    }

    public static void printMessage(String message) {
        System.out.print(message);
    }

    public static void printMessageLine(String message) {
        System.out.println(message);
    }

}
