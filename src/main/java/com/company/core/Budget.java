package com.company.core;

import com.company.core.enums.*;
import com.company.core.modules.RecordAbstract;
import com.company.core.modules.transactions.ExpenditureRecord;
import com.company.core.modules.transactions.IncomeRecord;
import com.company.core.repositories.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Budget {

    private BigDecimal balance;
    private TransactionRepository transactionRepository;
    private int localRecordCount = 0;

    public Budget() {
        balance = new BigDecimal("0");
        transactionRepository = new TransactionRepository();
    }

    public void addIncome(LocalDateTime date, BigDecimal sum, TransactionCategory category, PaymentMethod paymentMethod, IncomeType incomeType, String info) {
        RecordAbstract createdIncome = new IncomeRecord(localRecordCount, date, sum, category, paymentMethod, incomeType, info);
        transactionRepository.createTransaction(createdIncome);
        localRecordCount++;
    }

    public void addExpenditure(LocalDateTime date, BigDecimal sum, TransactionCategory category, PaymentMethod paymentMethod, ExpenditureType expenditureType, String info) {
        RecordAbstract createdExpenditure = new ExpenditureRecord(localRecordCount, date, sum, category, paymentMethod, expenditureType, info);
        transactionRepository.createTransaction(createdExpenditure);
        localRecordCount++;
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
     * provides String result of income
     *
     * @param startsFromOne
     * @return
     */
//    public List<String> fetchIncomeList(boolean startsFromOne) {
//        ArrayList<String> incomeOperations = getOperationListToStringList(transactionRepository.getRecordsByTransactionType(TransactionType.INCOME), startsFromOne);
//        return incomeOperations;
//    }
//
//    public List<String> fetchExpenseList(boolean startsFromOne) {
//        ArrayList<String> expenseOperations = getOperationListToStringList(transactionRepository.getRecordsByTransactionType(TransactionType.EXPENDITURE), startsFromOne);
//        return expenseOperations;
//    }

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
        return totalSumOfRecords((List<RecordAbstract>) (List<? extends RecordAbstract>) transactionRepository.getRecordsByTransactionType(TransactionType.INCOME));
    }

    private BigDecimal totalExpenses() {
        return totalSumOfRecords((List<RecordAbstract>) (List<? extends RecordAbstract>) transactionRepository.getRecordsByTransactionType(TransactionType.EXPENDITURE));
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
     * returns record by provided local id (any, income or expense)
     *
     * @param id
     * @return record matching id, or null if found not any
     */
    public RecordAbstract getRecordByLocalId(int id) {
        RecordAbstract matchingObject = transactionRepository.getRecordByLocalId(id);
        return matchingObject;
    }

    /**
     * returns record by provided local id (specified in income or expense list)
     *
     * @param id
     * @return record matching conditions, or null if found not any
     */
    private RecordAbstract getRecordByLocalIdSpecified(List<RecordAbstract> particularList, int id) {
        RecordAbstract matchingObject = particularList.stream().
                filter(p -> p.getLocalId() == id).
                findAny().orElse(null);
        return matchingObject;
    }

    /**
     * Deletes record by provided local id
     *
     * @param localId - local id of record
     * @return true if deletion successful, false if no such record found
     */
    public boolean deleteRecord(int localId) {
        try {
            transactionRepository.deleteRecordByLocalId(localId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Adds sample entries to this budget. For testing purposes
     */
    public void addSampleRecords() {
        this.addIncome(LocalDateTime.now(), new BigDecimal("2500"), TransactionCategory.ASSET, PaymentMethod.BANK_TRANSACTION, IncomeType.SALARY, "Salary");
        this.addIncome(LocalDateTime.now(), new BigDecimal("2800"), TransactionCategory.ASSET, PaymentMethod.BANK_TRANSACTION, IncomeType.SALARY, "Salary");
        this.addIncome(LocalDateTime.now(), new BigDecimal("2200"), TransactionCategory.ASSET, PaymentMethod.BANK_TRANSACTION, IncomeType.SALARY, "Salary");
        this.addExpenditure(LocalDateTime.now(), new BigDecimal("50"), TransactionCategory.EXPENSES, PaymentMethod.CASH, ExpenditureType.GROCERIES, "bottle of whiskey");
        this.addExpenditure(LocalDateTime.now(), new BigDecimal("15"), TransactionCategory.EXPENSES, PaymentMethod.CASH, ExpenditureType.LUXURY_GOODS, "a cigar");
    }

    public TransactionRepository getTransactionRepository() {
        return transactionRepository;
    }

    public int getLocalRecordCount() {
        return localRecordCount;
    }
}
