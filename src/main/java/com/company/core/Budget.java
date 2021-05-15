package com.company.core;

import com.company.core.abstracts.RecordAbstract;
import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;
import com.company.core.transactions.ExpenditureRecord;
import com.company.core.transactions.IncomeRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Budget {

    private BigDecimal balance;

    private List<IncomeRecord> incomeList;
    private List<ExpenditureRecord> expensesList;

    private int incomeRecordsTotal = 0;
    private int expenseRecordsTotal = 0;

    public Budget() {
        balance = new BigDecimal("0");
        incomeList = new ArrayList<>();
        expensesList = new ArrayList<>();
    }

    public void addIncome(LocalDateTime date, BigDecimal sum, TransactionCategory category, PaymentMethod paymentMethod, String info) {
        incomeList.add(new IncomeRecord(date, sum, category, paymentMethod, info));
    }

    public void addExpenditure(LocalDateTime date, BigDecimal sum, TransactionCategory category, PaymentMethod paymentMethod, String info) {
        expensesList.add(new ExpenditureRecord(date, sum, category, paymentMethod, info));
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
}
