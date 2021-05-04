package com.company.console;

import com.company.core.Budget;
import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        sc.close();
    }

    private void mainMenu() {
        sendMainMenuMessage();
        processMainMenuSelection();
    }

    private void processMainMenuSelection() {

        boolean selectedChoice = false;

        while (!selectedChoice) {
          int mainSelection = userInput.enterInt("Enter your choice: ");
            switch (mainSelection) {
                case 1:
                    selectedChoice = true;
                    addIncomeRecord();
                    break;
                case 2:
                    selectedChoice = true;
                    addExpenseRecord();
                    break;
                case 3:
                    selectedChoice = true;
                    listAllIncome();
                    break;
                case 4:
                    selectedChoice = true;
                    listAllExpenses();
                    break;
                default:
                    System.out.println("Wrong input, select right menu option");
            }
        }
    }

    private void listAllExpenses() {
        System.out.println("Total expenses");
    }

    private void listAllIncome() {
        System.out.println("Total income");
    }

    private void addExpenseRecord() {
        System.out.println("Add expense record");
        LocalDateTime providedDate = userInput.enterDateTime("Enter date");
        BigDecimal providedSum = userInput.enterBigDecimal("Enter sum");
        TransactionCategory providedCategory = userInput.enterCategory("Choose category");
        PaymentMethod providedPaymentMethod = userInput.enterPaymentMethod("Choose payment method");
        String providedInfo = userInput.enterString("Enter additional info");
        budget.addExpenditure(providedDate, providedSum, providedCategory, providedPaymentMethod, providedInfo);
    }

    private void addIncomeRecord() {
        System.out.println("Add income record");
        LocalDateTime providedDate = userInput.enterDateTime("Enter date");
        BigDecimal providedSum = userInput.enterBigDecimal("Enter sum");
        TransactionCategory providedCategory = userInput.enterCategory("Choose category");
        PaymentMethod providedPaymentMethod = userInput.enterPaymentMethod("Choose payment method");
        String providedInfo = userInput.enterString("Enter additional info");
        budget.addIncome(providedDate, providedSum, providedCategory, providedPaymentMethod, providedInfo);
    }



    private void sendMainMenuMessage() {
        printSeparator('*', 30);
        System.out.println("Main Menu:");
        System.out.println("Select option");
        System.out.println("1. Add income");
        System.out.println("2. Add expense");
        System.out.println("3. List all income");
        System.out.println("4. List all expenses");

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


}
