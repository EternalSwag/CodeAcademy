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
        scenarioSelectionMenu();
    }

    public void scenarioSelectionMenu()
    {
        ConsolePrinter.printMessage(Messages.SELECT_SCENARIO);
        int choice = userInput.enterInt(Messages.ENTER_YOUR_CHOICE);
        switch (choice)
        {
            case 1:
                mainMenu();
                break;
            case 2:
                budget.addSampleRecords();
                mainMenu();
                break;
            default:
                scenarioSelectionMenu();
        }
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
                ConsolePrinter.printMessageLine(listToString(budget.fetchIncomeList(true)));
                ConsolePrinter.printSeparator('-', 30);
                break;
            case EXPENDITURE:
                ConsolePrinter.printMessageLine(listToString(budget.fetchExpenseList(true)));
                ConsolePrinter.printSeparator('-', 30);
                break;
            default:
                deleteRecordSubmenu();
        }
        int choice = userInput.enterInt(Messages.ENTER_IDTODELETE);
        boolean deleteOperationResult = budget.deleteRecord(transactionType, choice);

        if (deleteOperationResult) {
            ConsolePrinter.printMessageLine(Messages.OPERATION_SUCCESFUL);
        } else {
            ConsolePrinter.printMessageLine(Messages.RECORD_DOESNT_EXIST);
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
        ConsolePrinter.printMessageLine(Messages.TOTAL_EXPENSES);
        ConsolePrinter.printMessageLine(listToString(budget.fetchExpenseList(true)));
        pressEnterKeyToContinue();
        mainMenu();
    }

    private void listAllIncomeSubmenu() {
        ConsolePrinter.printMessageLine(Messages.TOTAL_INCOME);
        ConsolePrinter.printMessageLine(listToString(budget.fetchIncomeList(true)));
        pressEnterKeyToContinue();
        mainMenu();
    }

    private void addExpenseRecordSubmenu() {
        ConsolePrinter.printMessageLine(Messages.ADD_EXPENSE_RECORD);
        LocalDateTime providedDate = userInput.enterDateTime();
        BigDecimal providedSum = userInput.enterBigDecimal(Messages.ENTER_SUM);
        TransactionCategory providedCategory = userInput.enterCategory();
        PaymentMethod providedPaymentMethod = userInput.enterPaymentMethod();
        String providedInfo = userInput.enterString(Messages.ENTER_ADDITIONAL_INFO);
        budget.addExpenditure(providedDate, providedSum, providedCategory, providedPaymentMethod, providedInfo);
        mainMenu();
    }

    private void addIncomeRecordSubmenu() {
        ConsolePrinter.printMessageLine(Messages.ADD_INCOME_RECORD);
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
        ConsolePrinter.printMessageLine(Messages.MAIN_MENU);
        ConsolePrinter.printMessageLine(budget.info());
        ConsolePrinter.printSeparator('*', 30);
        ConsolePrinter.printMessage(Messages.MAIN_MENU_SELECT_OPTION);
    }

    private void sendGreeting() {
        ConsolePrinter.printSeparator('*', 30);
        ConsolePrinter.printMessage(Messages.GREETING);
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
        ConsolePrinter.printMessage(Messages.PRESS_ENTER_KEY);
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

    /**
     * converts string list into single string, where every entry is separated with newline
     *
     * @param arrayProvided
     * @return
     */
    private String listToString(List<String> arrayProvided) {
        StringBuilder sb = new StringBuilder();
        for (String s : arrayProvided) {
            sb.append(s + "\n");
        }
        return sb.toString();
    }
}
