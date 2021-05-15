package com.company.console.menu;

import com.company.core.Budget;
import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;
import com.company.core.enums.TransactionType;

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
                addIncomeRecordSubmenu();
                break;
            case 2:
                addExpenseRecordSubmenu();
                break;
            case 3:
                listAllIncomeSubmenu();
                break;
            case 4:
                listAllExpensesSubmenu();
                break;
            case 5:
                deleteRecordSubmenu();
                break;
            case 0:
                exit();
                break;
            default:
                System.out.println("Wrong input, select right menu option");
                processMainMenuSelection();
        }
    }

    private void deleteRecordSubmenu() {

        deleteSubmenuMessage();
        int choice = userInput.enterInt("Enter your choice: ");
        switch (choice) {
            case 1:
                deleteMenu(TransactionType.INCOME);
                break;
            case 2:
                deleteMenu(TransactionType.EXPENDITURE);
                break;
            case 0:
                mainMenu();
                break;
            default:
                System.out.println("Wrong input, select right menu option");
                deleteRecordSubmenu();
        }
    }

    private void deleteMenu(TransactionType transactionType) {
        //display all records first
        switch (transactionType) {
            case INCOME:
                System.out.println(budget.fetchIncomeList(true));
                printSeparator('-', 30);
                break;
            case EXPENDITURE:
                System.out.println(budget.fetchExpenseList(true));
                printSeparator('-', 30);
                break;
            default:
                deleteRecordSubmenu();
        }
        int choice = userInput.enterInt("enter id of record you want to delete: ");
        boolean deleteOperationResult = budget.deleteRecord(transactionType, choice);

        if (deleteOperationResult) {
            System.out.println("Operation successful");
        } else {
            System.out.println("This record doesn't exist");
        }
        deleteRecordSubmenu();
    }

    private void deleteSubmenuMessage() {
        System.out.println("1. Delete income record");
        System.out.println("2. Delete expense record");
        System.out.println("0. Exit submenu");
    }

    private void exit() {
        System.out.println("Thanks for using our service");
        sc.close();
        System.exit(0);
    }

    private void listAllExpensesSubmenu() {
        System.out.println("Total expenses");
        System.out.println(arrayToString(budget.fetchExpenseList(true)));
        pressEnterKeyToContinue();
        mainMenu();
    }

    private void listAllIncomeSubmenu() {
        System.out.println("Total income");
        System.out.println(arrayToString(budget.fetchIncomeList(true)));
        pressEnterKeyToContinue();
        mainMenu();
    }

    private void addExpenseRecordSubmenu() {
        System.out.println("Add expense record");
        LocalDateTime providedDate = userInput.enterDateTime();
        BigDecimal providedSum = userInput.enterBigDecimal("Enter sum: ");
        TransactionCategory providedCategory = userInput.enterCategory();
        PaymentMethod providedPaymentMethod = userInput.enterPaymentMethod();
        String providedInfo = userInput.enterString("Enter additional info: ");
        budget.addExpenditure(providedDate, providedSum, providedCategory, providedPaymentMethod, providedInfo);
        mainMenu();
    }

    private void addIncomeRecordSubmenu() {
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
        System.out.println("5. Delete record");
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
