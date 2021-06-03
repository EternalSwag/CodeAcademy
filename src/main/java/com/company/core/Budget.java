package com.company.core;

import com.company.console.menu.ConsolePrinter;
import com.company.console.menu.colortext.ColorsText;
import com.company.core.enums.*;
import com.company.core.model.RecordAbstract;
import com.company.core.model.transactions.ExpenditureRecord;
import com.company.core.model.transactions.IncomeRecord;
import com.company.core.repositories.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Budget {

    private BigDecimal balance;
    private TransactionRepository transactionRepository;

    public Budget() {
        balance = new BigDecimal("0");
        transactionRepository = new TransactionRepository();
    }

    /**
     * provides list of all entries in operations list
     *
     * @param transactionList transaction list provided
     * @param startsFromOne   flag, indicating should outputs first index starts from 0 (false) or 1 (true)
     * @return list of strings
     */
    public ArrayList<String> getOperationListToStringList(List<RecordAbstract> transactionList, boolean startsFromOne) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < transactionList.size(); i++) {
            int index = startsFromOne ? i + 1 : i;
            result.add((index) + ". " + transactionList.get(i).toString() + "\n");
        }
        return result;
    }

    /**
     * @param transactionList transaction list provided
     * @param startsFromOne   flag, indicating should outputs first index starts from 0 (false) or 1 (true)
     * @return string
     */
    public String getOperationListToString(List<RecordAbstract> transactionList, boolean startsFromOne) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < transactionList.size(); i++) {
            int index = startsFromOne ? i + 1 : i;
            result.append((index) + ". " + transactionList.get(i).toString() + "\n");
        }
        return result.toString();
    }

    /**
     * updates balance and provides string of basic information of budget
     */
    public String info() {
        updateBalance();
        String result = String.format("Balance = " + balance + "\n" + "Total income = " + totalIncome().toString() + "\n" + "Total expenses = " + totalExpenses().toString());
        return result;
    }

    /**
     * recalculates balance
     */
    private void updateBalance() {
        this.balance = totalIncome().subtract(totalExpenses());
    }

    private BigDecimal totalIncome() {
        return totalSumOfRecords(transactionRepository.getRecordsByTransactionType(TransactionType.INCOME));
    }

    private BigDecimal totalExpenses() {
        return totalSumOfRecords(transactionRepository.getRecordsByTransactionType(TransactionType.EXPENDITURE));
    }

    /**
     * provides total sum of provided records. It doesn't care if its expenses or income, so filter beforehand
     *
     * @param recordList
     * @return
     */
    private BigDecimal totalSumOfRecords(List<RecordAbstract> recordList) {
        BigDecimal result = new BigDecimal("0");
        for (RecordAbstract record : recordList) {
            result = result.add(record.getSum());
        }
        return result;
    }

    /**
     * Adds sample entries to this budget. For testing purposes
     */
    public void addSampleRecords() throws Exception {
        try {

            transactionRepository.createTransaction(new IncomeRecord(LocalDateTime.now(), new BigDecimal("2500"), TransactionCategory.ASSET, PaymentMethod.BANK_TRANSACTION, TransactionType.INCOME, IncomeType.SALARY, "Salary - another day another dollar"));
            transactionRepository.createTransaction(new IncomeRecord(LocalDateTime.now(), new BigDecimal("2800"), TransactionCategory.ASSET, PaymentMethod.BANK_TRANSACTION, TransactionType.INCOME, IncomeType.SALARY, "Salary - hard work"));
            transactionRepository.createTransaction(new IncomeRecord(LocalDateTime.now(), new BigDecimal("3300"), TransactionCategory.ASSET, PaymentMethod.BANK_TRANSACTION, TransactionType.INCOME, IncomeType.SALARY, "Salary - good bux"));

            transactionRepository.createTransaction(new ExpenditureRecord(LocalDateTime.now(), new BigDecimal("50"), TransactionCategory.ASSET, PaymentMethod.BANK_TRANSACTION, TransactionType.EXPENDITURE, ExpenditureType.GROCERIES, "Bottle of whiskey"));
            transactionRepository.createTransaction(new ExpenditureRecord(LocalDateTime.now(), new BigDecimal("20"), TransactionCategory.ASSET, PaymentMethod.BANK_TRANSACTION, TransactionType.EXPENDITURE,ExpenditureType.LUXURY_GOODS, "A cigar"));
        } catch (Exception e) {
            ConsolePrinter.printMessageLine(ColorsText.ANSI_RED, e.getMessage());
        }
    }

    public TransactionRepository getTransactionRepository() {
        return transactionRepository;
    }

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
