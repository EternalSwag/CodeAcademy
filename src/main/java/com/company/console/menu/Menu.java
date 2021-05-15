package com.company.console.menu;

import com.company.core.Budget;
import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;
import com.company.core.enums.TransactionType;

import java.io.Console;
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

        int mainSelection = userInput.enterInt(Messages.ENTER_YOUR_CHOICE);
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
                ConsolePrinter.printMessageLine(Messages.MENU_WRONG_INPUT);
                processMainMenuSelection();
        }
    }

    private void deleteRecordSubmenu() {

        deleteSubmenuMessage();
        int choice = userInput.enterInt(Messages.ENTER_YOUR_CHOICE);
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
                ConsolePrinter.printMessageLine(Messages.MENU_WRONG_INPUT);
                deleteRecordSubmenu();
        }
    }

    private void deleteMenu(TransactionType transactionType) {
        //display all records first
        switch (transactionType) {
            case INCOME:
                System.out.println(arrayToString(budget.fetchIncomeList(true)));
                ConsolePrinter.printSeparator('-', 30);
                break;
            case EXPENDITURE:
                System.out.println(arrayToString(budget.fetchExpenseList(true)));
                ConsolePrinter.printSeparator('-', 30);
                break;
            default:
                deleteRecordSubmenu();
        }
        int choice = userInput.enterInt(Messages.ENTER_IDTODELETE);
        boolean deleteOperationResult = budget.deleteRecord(transactionType, choice);

        if (deleteOperationResult) {
            System.out.println(Messages.OPERATION_SUCCESFUL);
        } else {
            System.out.println(Messages.RECORD_DOESNT_EXIST);
        }
        deleteRecordSubmenu();
    }

    private void deleteSubmenuMessage() {
        ConsolePrinter.printMessage(Messages.DELETE_SUBMENU_MESSAGE);
    }

    private void exit() {
        ConsolePrinter.printMessage(Messages.MENU_EXIT_MESSAGE);
        sc.close();
        System.exit(0);
    }

    private void listAllExpensesSubmenu() {
        System.out.println(Messages.TOTAL_EXPENSES);
        System.out.println(arrayToString(budget.fetchExpenseList(true)));
        pressEnterKeyToContinue();
        mainMenu();
    }

    private void listAllIncomeSubmenu() {
        System.out.println(Messages.TOTAL_INCOME);
        System.out.println(arrayToString(budget.fetchIncomeList(true)));
        pressEnterKeyToContinue();
        mainMenu();
    }

    private void addExpenseRecordSubmenu() {
        System.out.println(Messages.ADD_EXPENSE_RECORD);
        LocalDateTime providedDate = userInput.enterDateTime();
        BigDecimal providedSum = userInput.enterBigDecimal("Enter sum: ");
        TransactionCategory providedCategory = userInput.enterCategory();
        PaymentMethod providedPaymentMethod = userInput.enterPaymentMethod();
        String providedInfo = userInput.enterString("Enter additional info: ");
        budget.addExpenditure(providedDate, providedSum, providedCategory, providedPaymentMethod, providedInfo);
        mainMenu();
    }

    private void addIncomeRecordSubmenu() {
        System.out.println(Messages.ADD_INCOME_RECORD);
        LocalDateTime providedDate = userInput.enterDateTime();
        BigDecimal providedSum = userInput.enterBigDecimal(Messages.ENTER_SUM);
        TransactionCategory providedCategory = userInput.enterCategory();
        PaymentMethod providedPaymentMethod = userInput.enterPaymentMethod();
        String providedInfo = userInput.enterString(Messages.ENTER_ADDITIONAL_INFO);
        budget.addIncome(providedDate, providedSum, providedCategory, providedPaymentMethod, providedInfo);
        mainMenu();
    }


    private void sendMainMenuMessage() {
        ConsolePrinter.printSeparator('*', 30);
        System.out.println("Main Menu:");
        System.out.println(budget.info());
        ConsolePrinter.printSeparator('*', 30);
        System.out.println("Select option");
        System.out.println("1. Add income");
        System.out.println("2. Add expense");
        System.out.println("3. List all income");
        System.out.println("4. List all expenses");
        System.out.println("5. Delete record");
        System.out.println("0. Exit");
    }

    private void sendGreeting() {
        ConsolePrinter.printSeparator('*', 30);
        System.out.println("Welcome to budget planning application");
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

    /**
     * converts string array into string, where every entry is separated with newline
     * @param arrayProvided
     * @return
     */
    private String arrayToString(List<String> arrayProvided) {
        StringBuilder sb = new StringBuilder();
        for (String s : arrayProvided) {
            sb.append(s + "\n");
        }
        return sb.toString();
    }

}
