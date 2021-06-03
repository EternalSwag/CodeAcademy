package com.company.console.menu;

import com.company.console.menu.colortext.ColorsText;

public class ConsolePrinter {

    /**
     * horizontal symbol line, to separate text in console output
     *
     * @param ch  character line consists of
     * @param num number of characters
     */
    public static void printSeparator(char ch, int num) {
        for (int j = 0; j < num; j++) {
            System.out.print(ch);
        }
        System.out.println("");
    }

    /**
     * horizontal symbol line, to separate text in console output
     *
     * @param color unicode color code
     * @param ch    character line consists of
     * @param num   number of characters
     */
    public static void printSeparator(String color, char ch, int num) {
        for (int j = 0; j < num; j++) {
            System.out.print(color + ch + ColorsText.ANSI_RESET);
        }
        System.out.println("");
    }


    /**
     * prints message in console output without new line in the end
     *
     * @param message string content
     */
    public static void printMessage(String message) {
        System.out.print(message);
    }

    /**
     * prints message in console output without new line in the end
     *
     * @param color   unicode color code
     * @param message string content
     */
    public static void printMessage(String color, String message) {
        System.out.print(color + message + ColorsText.ANSI_RESET);
    }

    /**
     * prints message in console output with new line in the end
     *
     * @param message string content
     */
    public static void printMessageLine(String message) {
        System.out.println(message);
    }

    /**
     * prints message in console output with new line in the end
     *
     * @param color   unicode color code
     * @param message string content
     */
    public static void printMessageLine(String color, String message) {
        System.out.println(color + message + ColorsText.ANSI_RESET);
    }
}
