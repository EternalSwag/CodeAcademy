package com.company.console.menu;

import com.company.core.Budget;
import com.company.core.abstracts.RecordAbstract;
import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private Scanner sc = new Scanner(System.in);
    private Budget budget;
    private UserConsoleInput userInput = new UserConsoleInput(sc);

    public Menu(Budget budget) {

        if (budget == null) {
            System.exit(0);
        }

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
        System.out.println(arrayToString(budget.fetchExpenseList(true)));
        pressEnterKeyToContinue();
        mainMenu();
    }

    private void listAllIncome() {
        System.out.println("Total income");
        System.out.println(arrayToString(budget.fetchExpenseList(true)));
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

    /**
     * it takes operation list, and adds index starting from 1
     *
     * @param operationsList
     * @return formatted String list
     */
    private String formatListOfRecords(List<String> operationsList) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < operationsList.size(); i++) {
            String formattedLine = String.format("%d. %s", (i + 1), operationsList.get(i));
        }

        return result.toString();
    }


    private void pressEnterKeyToContinue() {
        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

    private String arrayToString(List<String> arrayProvided) {
        StringBuilder sb = new StringBuilder();
        for (String s : arrayProvided) {
            sb.append(s + "\n");
        }
        return sb.toString();
    }

}
