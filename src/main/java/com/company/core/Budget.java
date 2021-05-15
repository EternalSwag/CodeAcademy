package com.company.core;

import com.company.core.abstracts.RecordAbstract;
import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;
import com.company.core.transactions.ExpenditureRecord;
import com.company.core.transactions.IncomeRecord;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Budget {

    private BigDecimal balance;
    private List<IncomeRecord> incomeList;
    private List<ExpenditureRecord> expensesList;
    private int localRecordCount = 0;

    public Budget() {
        balance = new BigDecimal("0");
        incomeList = new ArrayList<>();
        expensesList = new ArrayList<>();
    }

    public void addIncome(LocalDateTime date, BigDecimal sum, TransactionCategory category, PaymentMethod paymentMethod, String info) {
        incomeList.add(new IncomeRecord(localRecordCount, date, sum, category, paymentMethod, info));
        localRecordCount++;
    }

    public void addExpenditure(LocalDateTime date, BigDecimal sum, TransactionCategory category, PaymentMethod paymentMethod, String info) {
        expensesList.add(new ExpenditureRecord(localRecordCount, date, sum, category, paymentMethod, info));
        localRecordCount++;
    }


    /**
     * provides list of all entries in operations list
     *
     * @param recordList    record list
     * @param startsFromOne flag, indicating should outputs first index starts from 0 (false) or 1 (true)
     * @return list of strings
     */
    public ArrayList<String> getOperationListToStringList(List<RecordAbstract> recordList, boolean startsFromOne) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            int index = startsFromOne ? i + 1 : i;
            result.add((index) + ". " + recordList.get(i).toString() + "\n");
        }
        return result;
    }

    /**
     * @param recordList    record list
     * @param startsFromOne flag, indicating should outputs first index starts from 0 (false) or 1 (true)
     * @return string
     */
    public String getOperationListToString(List<RecordAbstract> recordList, boolean startsFromOne) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < recordList.size(); i++) {
            int index = startsFromOne ? i + 1 : i;
            result.append((index) + ". " + recordList.get(i).toString() + "\n");
        }
        return result.toString();
    }

    /**
     * provides String result of income
     *
     * @param startsFromOne
     * @return
     */
    public List<String> fetchIncomeList(boolean startsFromOne) {
        ArrayList<String> incomeOperations = getOperationListToStringList((List<RecordAbstract>) (List<? extends RecordAbstract>) incomeList, startsFromOne);
        return incomeOperations;
    }

    public List<String> fetchExpenseList(boolean startsFromOne) {
        ArrayList<String> expenseOperations = getOperationListToStringList((List<RecordAbstract>) (List<? extends RecordAbstract>) expensesList, startsFromOne);
        return expenseOperations;
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
        return totalSumOfRecords((List<RecordAbstract>) (List<? extends RecordAbstract>) incomeList);
    }

    private BigDecimal totalExpenses() {
        return totalSumOfRecords((List<RecordAbstract>) (List<? extends RecordAbstract>) expensesList);
    }

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
        RecordAbstract matchingObject = incomeList.stream().
                filter(p -> p.getLocalId() == id).
                findAny().orElse(null);

        if (matchingObject == null) {
            matchingObject = expensesList.stream().
                    filter(p -> p.getLocalId() == id).
                    findAny().orElse(null);
        }
        return matchingObject;
    }

    /**
     * returns record by provided local id (specified in income or expense list)
     *
     * @param id
     * @return record matching conditions, or null if found not any
     */
    public RecordAbstract getRecordByLocalIdSpecified(List<RecordAbstract> particularList, int id) {
        RecordAbstract matchingObject = particularList.stream().
                filter(p -> p.getLocalId() == id).
                findAny().orElse(null);
        return matchingObject;
    }

    /**
     * Deletes record by provided local id
     *
     * @param listProvided - income, expense list, etc
     * @param localId      - id of record
     * @return true if deletion successful, false if no such record found
     */
    public boolean deleteRecord(List<RecordAbstract> listProvided, int localId) {
        RecordAbstract entryToDelete = getRecordByLocalIdSpecified(listProvided, localId);

        if (entryToDelete == null) {
            return false;
        }
        listProvided.remove(entryToDelete);
    }
}
