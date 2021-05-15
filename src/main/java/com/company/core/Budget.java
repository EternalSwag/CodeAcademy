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

    private List<IncomeRecord> income;
    private List<ExpenditureRecord> expenses;

    private int incomeRecordsTotal = 0;
    private int expenseRecordsTotal = 0;

    public Budget() {
        balance = new BigDecimal("0");
        income = new ArrayList<>();
        expenses = new ArrayList<>();
    }

    public void addIncome(LocalDateTime date, BigDecimal sum, TransactionCategory category, PaymentMethod paymentMethod, String info) {
        income.add(new IncomeRecord(date, sum, category, paymentMethod, info));
        incomeRecordsTotal++;
    }

    public void addExpenditure(LocalDateTime date, BigDecimal sum, TransactionCategory category, PaymentMethod paymentMethod, String info) {
        expenses.add(new ExpenditureRecord(date, sum, category, paymentMethod, info));
        expenseRecordsTotal++;
    }

    public String listAllIncome() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < incomeRecordsTotal; i++) {
            result.append((i) + ". " + income.get(i).toString() + "\n");
        }
        return result.toString();
    }

    public String listAllExpenses() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < expenseRecordsTotal; i++) {
            result.append((i) + ". " + expenses.get(i).toString() + "\n");
        }
        return result.toString();
    }

    public String info() {

        BigDecimal incomeTotal = totalBalance((List<RecordAbstract>) (List<? extends RecordAbstract>) income);
        BigDecimal expenseTotal = totalBalance((List<RecordAbstract>) (List<? extends RecordAbstract>) expenses);

        BigDecimal balance = incomeTotal.subtract(expenseTotal);

        String result = String.format("Balance = " + balance + "\n" + "Total income = " + incomeTotal.toString() + "\n" + "Total expenses = " + expenseTotal.toString());
        return result;
    }

    private BigDecimal totalBalance(List<RecordAbstract> recordList) {
        BigDecimal result = new BigDecimal("0");
        for (RecordAbstract record : recordList) {
            result = result.add(record.getSum());
        }
        return result;
    }
}
