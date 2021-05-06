package com.company.console;

import com.company.core.Budget;
import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Menu {
    private Scanner sc = new Scanner(System.in);
    private Budget budget;
    private UserConsoleInput userInput = new UserConsoleInput(sc);

    public Menu(Budget budget) {
        this.budget = budget;
    }

    public void entryMenu() {
        sendGreeting();
        mainMenu();
    }

    private void mainMenu() {
        sendMainMenuMessage();
        processMainMenuSelection();
    }

    private void processMainMenuSelection() {

          int mainSelection = userInput.enterInt("Enter your choice: ");
            switch (mainSelection) {
                case 1:
                    addIncomeRecord();
                    break;
                case 2:
                    addExpenseRecord();
                    break;
                case 3:
                    listAllIncome();
                    break;
                case 4:
                    listAllExpenses();
                    break;
                case 0:
                    exit();
                    break;
                default:
                    System.out.println("Wrong input, select right menu option");
                    processMainMenuSelection();
            }
        }


    private void exit() {
        System.out.println("Thanks for using our service");
        sc.close();
        System.exit(0);
    }

    private void listAllExpenses() {
        System.out.println("Total expenses");
        System.out.println(budget.listAllExpenses());
        pressEnterKeyToContinue();
        mainMenu();
    }

    private void listAllIncome() {
        System.out.println("Total income");
        System.out.println(budget.listAllIncome());
        pressEnterKeyToContinue();
        mainMenu();
    }

    private void addExpenseRecord() {
        System.out.println("Add expense record");
        LocalDateTime providedDate = userInput.enterDateTime();
        BigDecimal providedSum = userInput.enterBigDecimal("Enter sum: ");
        TransactionCategory providedCategory = userInput.enterCategory();
        PaymentMethod providedPaymentMethod = userInput.enterPaymentMethod();
        String providedInfo = userInput.enterString("Enter additional info: ");
        budget.addExpenditure(providedDate, providedSum, providedCategory, providedPaymentMethod, providedInfo);
        mainMenu();
    }

    private void addIncomeRecord() {
        System.out.println("Add income record");
        LocalDateTime providedDate = userInput.enterDateTime();
        BigDecimal providedSum = userInput.enterBigDecimal("Enter sum: ");
        TransactionCategory providedCategory = userInput.enterCategory();
        PaymentMethod providedPaymentMethod = userInput.enterPaymentMethod();
        String providedInfo = userInput.enterString("Enter additional info: ");
        budget.addIncome(providedDate, providedSum, providedCategory, providedPaymentMethod, providedInfo);
        mainMenu();
    }



    private void sendMainMenuMessage() {
        printSeparator('*', 30);
        System.out.println("Main Menu:");
        System.out.println(budget.info());
        printSeparator('*', 30);
        System.out.println("Select option");
        System.out.println("1. Add income");
        System.out.println("2. Add expense");
        System.out.println("3. List all income");
        System.out.println("4. List all expenses");
        System.out.println("0. Exit");
    }

    private void sendGreeting() {
        printSeparator('*', 30);
        System.out.println("Welcome to budget planning application");
    }

    private void printSeparator(char c, int i) {
        for (int j = 0; j < i; j++) {
            System.out.print(c);
        }
        System.out.println("");
    }


    private void pressEnterKeyToContinue()
    {
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {}
    }

}
