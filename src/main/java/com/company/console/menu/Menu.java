package com.company.console.menu;

import com.company.console.menu.colortext.ColorsBackground;
import com.company.console.menu.colortext.ColorsText;
import com.company.core.Budget;
import com.company.core.enums.*;
import com.company.core.modules.RecordAbstract;

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
        scenarioSelectionMenu();
    }

    public void scenarioSelectionMenu() {
        ConsolePrinter.printMessage(ColorsText.ANSI_YELLOW, Messages.SELECT_SCENARIO);
        int choice = userInput.enterInt(Messages.ENTER_YOUR_CHOICE);
        switch (choice) {
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
                updateRecordSubmenu();
            case 6:
                deleteRecordSubmenu();
                break;
            case 0:
                exit();
                break;
            default:
                ConsolePrinter.printMessageLine(ColorsText.ANSI_RED, Messages.MENU_WRONG_INPUT);
                processMainMenuSelection();
        }
    }

    private void updateRecordSubmenu() {
        int mainSelection = userInput.enterInt(Messages.ENTER_ID_OF_RECORD_TO_UPDATE);
        RecordAbstract selectedTransaction = budget.getTransactionRepository().getRecordByLocalId(mainSelection);
        if (selectedTransaction != null) {
            TransactionType transactionType = selectedTransaction.getTransactionType();
            switch (transactionType) {
                case INCOME:
                    updateIncomeRecordByEachFieldMenu(mainSelection);
                    break;
                case EXPENDITURE:
                    updateExpenditureRecordByEachFieldMenu(mainSelection);
                    break;
                default:
                    ConsolePrinter.printMessageLine(Messages.UNKNOWN_TRANSACTION_TYPE);
            }
        } else {
            ConsolePrinter.printMessageLine("The transaction with id " + mainSelection + " doesn't exist");
        }
        mainMenu();
    }

    private void updateExpenditureRecordByEachFieldMenu(int localTransactionId) {
        System.out.println("expense edit");
    }

    private void updateIncomeRecordByEachFieldMenu(int localTransactionId) {
        System.out.println("income edit");
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
                ConsolePrinter.printMessageLine(ColorsText.ANSI_RED, Messages.MENU_WRONG_INPUT);
                deleteRecordSubmenu();
        }
    }

    private void deleteMenu(TransactionType transactionType) {
        //display all records first
        switch (transactionType) {
            case INCOME:
                ConsolePrinter.printMessageLine(listToString(budget.fetchIncomeList(true)));
                ConsolePrinter.printSeparator('-', 50);
                break;
            case EXPENDITURE:
                ConsolePrinter.printMessageLine(listToString(budget.fetchExpenseList(true)));
                ConsolePrinter.printSeparator('-', 50);
                break;
            default:
                deleteRecordSubmenu();
        }
        int choice = userInput.enterInt(Messages.ENTER_IDTODELETE);

        boolean deleteOperationResult = budget.deleteRecord(choice);

        if (deleteOperationResult) {
            ConsolePrinter.printMessageLine(ColorsText.ANSI_GREEN, Messages.OPERATION_SUCCESFUL);
        } else {
            ConsolePrinter.printMessageLine(ColorsText.ANSI_RED, Messages.RECORD_DOESNT_EXIST);
        }
        deleteRecordSubmenu();
    }

    private void deleteSubmenuMessage() {
        ConsolePrinter.printMessage(ColorsText.ANSI_YELLOW, Messages.DELETE_SUBMENU_MESSAGE);
    }

    private void exit() {
        ConsolePrinter.printMessage(ColorsText.ANSI_YELLOW, Messages.MENU_EXIT_MESSAGE);
        sc.close();
        System.exit(0);
    }

    private void listAllExpensesSubmenu() {
        ConsolePrinter.printMessageLine(ColorsText.ANSI_YELLOW, Messages.TOTAL_EXPENSES);
        ConsolePrinter.printMessageLine(listToString(budget.fetchExpenseList(true)));
        pressEnterKeyToContinue();
        mainMenu();
    }

    private void listAllIncomeSubmenu() {
        ConsolePrinter.printMessageLine(ColorsText.ANSI_YELLOW, Messages.TOTAL_INCOME);
        ConsolePrinter.printMessageLine(listToString(budget.fetchIncomeList(true)));
        pressEnterKeyToContinue();
        mainMenu();
    }

    private void addExpenseRecordSubmenu() {
        ConsolePrinter.printMessageLine(ColorsText.ANSI_YELLOW, Messages.ADD_EXPENSE_RECORD);
        LocalDateTime providedDate = userInput.enterDateTime();
        BigDecimal providedSum = userInput.enterBigDecimal(Messages.ENTER_SUM);
        TransactionCategory providedCategory = userInput.enterCategory();
        PaymentMethod providedPaymentMethod = userInput.enterPaymentMethod();
        String providedInfo = userInput.enterString(Messages.ENTER_ADDITIONAL_INFO);
        ExpenditureType expenditureType = userInput.enterExpenditureType();
        budget.addExpenditure(providedDate, providedSum, providedCategory, providedPaymentMethod, expenditureType, providedInfo);
        mainMenu();
    }

    private void addIncomeRecordSubmenu() {
        ConsolePrinter.printMessageLine(ColorsText.ANSI_YELLOW, Messages.ADD_INCOME_RECORD);
        LocalDateTime providedDate = userInput.enterDateTime();
        BigDecimal providedSum = userInput.enterBigDecimal(Messages.ENTER_SUM);
        TransactionCategory providedCategory = userInput.enterCategory();
        PaymentMethod providedPaymentMethod = userInput.enterPaymentMethod();
        String providedInfo = userInput.enterString(Messages.ENTER_ADDITIONAL_INFO);
        IncomeType incomeType = userInput.enterIncomeType();
        budget.addIncome(providedDate, providedSum, providedCategory, providedPaymentMethod, incomeType, providedInfo);
        mainMenu();
    }


    private void sendMainMenuMessage() {
        ConsolePrinter.printSeparator('*', 50);
        ConsolePrinter.printMessageLine(ColorsText.ANSI_YELLOW, Messages.MAIN_MENU);
        ConsolePrinter.printMessageLine(ColorsText.ANSI_YELLOW, budget.info());
        ConsolePrinter.printSeparator('*', 50);
        ConsolePrinter.printMessage(Messages.MAIN_MENU_SELECT_OPTION);
    }

    private void sendGreeting() {
        ConsolePrinter.printSeparator(ColorsText.ANSI_BLUE, '-', 50);
        ConsolePrinter.printMessageLine(ColorsText.ANSI_YELLOW, Messages.GREETING);
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
        ConsolePrinter.printMessage(ColorsText.ANSI_YELLOW, Messages.PRESS_ENTER_KEY);
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
