package com.company;

import com.company.console.UserConsoleInput;

import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Hello Rimgaudas! master");
        System.out.println("Hello Rimgaudas! test branch");

        UserConsoleInput userInput = new UserConsoleInput(sc);


        userInput.enterDateTime("sdadsa");
    }
}
