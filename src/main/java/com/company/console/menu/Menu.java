package com.company.console.menu;

import com.company.config.Constants;
import com.company.console.menu.colortext.ColorsText;
import com.company.core.Budget;
import com.company.core.enums.*;
import com.company.core.model.RecordAbstract;
import com.company.core.model.transactions.ExpenditureRecord;
import com.company.core.model.transactions.IncomeRecord;
import com.company.core.services.TransactionDataOutput;
import com.company.core.services.fileio.CsvOperationsRead;
import com.company.core.services.fileio.CsvOperationsWrite;

import java.lang.module.Configuration;
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
        try {
            scenarioSelectionMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void scenarioSelectionMenu() throws Exception {
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

    private void mainMenu() throws Exception {
        sendMainMenuMessage();
        processMainMenuSelection();
    }

    private void processMainMenuSelection() throws Exception {

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
            case 7:
                writeAllRecordsToFileMenu();
                break;
            case 8:
                readAllRecordsFromFileMenu();
                break;
            case 0:
                exit();
                break;
            default:
                ConsolePrinter.printMessageLine(ColorsText.ANSI_RED, Messages.MENU_WRONG_INPUT);
                processMainMenuSelection();
        }
    }

    private void readAllRecordsFromFileMenu() throws Exception {
         budget.setTransactionRepository(CsvOperationsRead.getTransactionListFromFile(Constants.BUDGET_FILE_PATH, 0));
    mainMenu();
    }

    private void writeAllRecordsToFileMenu() throws Exception {
        CsvOperationsWrite.writeToFileTransactionRepository(budget.getTransactionRepository(), Constants.BUDGET_FILE_PATH);
        mainMenu();
    }



    private void updateRecordSubmenu() throws Exception {
        int selectedId = userInput.enterInt(Messages.ENTER_ID_OF_RECORD_TO_UPDATE);
        RecordAbstract selectedTransaction = budget.getTransactionRepository().getRecordByLocalId(selectedId);
        if (selectedTransaction != null) {
            TransactionType transactionType = selectedTransaction.getTransactionType();
            switch (transactionType) {
                case INCOME:
                    updateRecordByEachFieldMenu(TransactionType.INCOME, selectedId);
                    break;
                case EXPENDITURE:
                    updateRecordByEachFieldMenu(TransactionType.EXPENDITURE, selectedId);
                    break;
                default:
                    ConsolePrinter.printMessageLine(Messages.UNKNOWN_TRANSACTION_TYPE);
                    throw new Exception("Menu->updateRecordSubmenu: id " + selectedId + ", " + Messages.UNKNOWN_TRANSACTION_TYPE);
            }
        } else {
            throw new Exception("Menu->updateRecordSubmenu: the transaction with id " + selectedId + " doesn't exist");
        }
        mainMenu();
    }

    /**
     * prompts record data user is editing, makes him enter new data, and overwrites it
     * @param transactionType
     * @param localTransactionId
     * @throws Exception
     */
    private void updateRecordByEachFieldMenu(TransactionType transactionType, int localTransactionId) throws Exception {
        ConsolePrinter.printMessageLine(ColorsText.ANSI_RED, budget.getTransactionRepository().getRecordByLocalId(localTransactionId).toString());
        ConsolePrinter.printMessageLine(Messages.YOU_ARE_NOW_EDITING_THIS_RECORD);
        RecordAbstract updatedIncomeRecord = null;
        switch (transactionType) {
            case INCOME:
                updatedIncomeRecord = menuEnterIncome(localTransactionId);
                break;
            case EXPENDITURE:
                updatedIncomeRecord = menuEnterExpenditure(localTransactionId);
                break;
            default:
                throw new Exception("Menu->updateRecordByEachFieldMenu: unknown transaction type provided");
        }
        budget.getTransactionRepository().updateRecord(localTransactionId, updatedIncomeRecord);
    }


    /**
     * asks user which type of record they want to delete
     *
     * @throws Exception
     */
    private void deleteRecordSubmenu() throws Exception {
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

    /**
     * displays records by transaction type and prompts user to choose which one to delete.
     * should be 2 methods probably
     *
     * @param transactionType
     * @throws Exception
     */
    private void deleteMenu(TransactionType transactionType) throws Exception {
        //display all records first
        switch (transactionType) {
            case INCOME:
                ConsolePrinter.printMessageLine(TransactionDataOutput.listTransactionsAsString(budget.getTransactionRepository(), TransactionType.INCOME));
                ConsolePrinter.printSeparator('-', 50);
                break;
            case EXPENDITURE:
                ConsolePrinter.printMessageLine(TransactionDataOutput.listTransactionsAsString(budget.getTransactionRepository(), TransactionType.EXPENDITURE));
                ConsolePrinter.printSeparator('-', 50);
                break;
            default:
                deleteRecordSubmenu();
        }
        int choice = userInput.enterInt(Messages.ENTER_IDTODELETE);

        try {
            budget.getTransactionRepository().deleteRecordByLocalId(choice);
            ConsolePrinter.printMessageLine(ColorsText.ANSI_GREEN, Messages.OPERATION_SUCCESFUL);
        } catch (Exception e) {
            ConsolePrinter.printMessageLine(ColorsText.ANSI_RED, e.getMessage());
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

    /**
     * lists all expenditure in console
     *
     * @throws Exception
     */
    private void listAllExpensesSubmenu() throws Exception {
        ConsolePrinter.printMessageLine(ColorsText.ANSI_YELLOW, Messages.TOTAL_EXPENSES);
        ConsolePrinter.printMessage(TransactionDataOutput.listTransactionsAsString(budget.getTransactionRepository(), TransactionType.EXPENDITURE));
        pressEnterKeyToContinue();
        mainMenu();
    }

    /**
     * lists all income in console
     *
     * @throws Exception
     */
    private void listAllIncomeSubmenu() throws Exception {
        ConsolePrinter.printMessageLine(ColorsText.ANSI_YELLOW, Messages.TOTAL_INCOME);
        ConsolePrinter.printMessage(TransactionDataOutput.listTransactionsAsString(budget.getTransactionRepository(), TransactionType.INCOME));
        pressEnterKeyToContinue();
        mainMenu();
    }

    /**
     * adds record to repository
     *
     * @param transactionType income or expenditure
     * @throws Exception
     */
    private void addRecordMenu(TransactionType transactionType) throws Exception {
        RecordAbstract resultRecord = null;
        switch (transactionType) {
            case INCOME:
                resultRecord = menuEnterIncome(budget.getTransactionRepository().getLocalIdCounter() + 1);
                break;
            case EXPENDITURE:
                resultRecord = menuEnterExpenditure(budget.getTransactionRepository().getLocalIdCounter() + 1);
                break;
            default:
                throw new Exception("Menu->addRecordMenu: unknown transaction type");
        }
        budget.getTransactionRepository().createTransaction(resultRecord);
        mainMenu();
    }

    /**
     * series of menu input to make income transaction
     *
     * @return result income transaction
     */
    private IncomeRecord menuEnterIncome(int localIdProvided) {
        LocalDateTime providedDate = userInput.enterDateTime();
        BigDecimal providedSum = userInput.enterBigDecimal(Messages.ENTER_SUM);
        TransactionCategory providedCategory = userInput.enterCategory();
        PaymentMethod providedPaymentMethod = userInput.enterPaymentMethod();
        IncomeType incomeType = userInput.enterIncomeType();
        String providedInfo = userInput.enterString(Messages.ENTER_ADDITIONAL_INFO);
        return new IncomeRecord(localIdProvided, providedDate, providedSum, providedCategory, providedPaymentMethod, incomeType, providedInfo);
    }

    /**
     * series of menu input to make expenditure transaction
     *
     * @return result expenditure transaction
     */
    private ExpenditureRecord menuEnterExpenditure(int localIdProvided) {
        LocalDateTime providedDate = userInput.enterDateTime();
        BigDecimal providedSum = userInput.enterBigDecimal(Messages.ENTER_SUM);
        TransactionCategory providedCategory = userInput.enterCategory();
        PaymentMethod providedPaymentMethod = userInput.enterPaymentMethod();
        ExpenditureType expenditureType = userInput.enterExpenditureType();
        String providedInfo = userInput.enterString(Messages.ENTER_ADDITIONAL_INFO);
        return new ExpenditureRecord(localIdProvided, providedDate, providedSum, providedCategory, providedPaymentMethod, expenditureType, providedInfo);
    }


    private void addExpenseRecordSubmenu() throws Exception {
        addRecordMenu(TransactionType.EXPENDITURE);
    }

    private void addIncomeRecordSubmenu() throws Exception {
        addRecordMenu(TransactionType.INCOME);
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
}
