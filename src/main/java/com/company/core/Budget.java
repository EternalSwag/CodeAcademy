package com.company.core;

import com.company.core.abstracts.RecordAbstract;
import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;
import com.company.core.transactions.ExpenditureRecord;
import com.company.core.transactions.IncomeRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Budget {

    private BigDecimal balance;
    private IncomeRecord[] income;
    private ExpenditureRecord[] expenses;

    private int incomeRecordsTotal = 0;
    private int expenseRecordsTotal = 0;

    public Budget() {
        balance = new BigDecimal("0");
        income = new IncomeRecord[100];
        expenses = new ExpenditureRecord[100];
    }

    public void addIncome(LocalDateTime date, BigDecimal sum, TransactionCategory category, PaymentMethod paymentMethod, String info) {
        income[incomeRecordsTotal] = new IncomeRecord(date, sum, category, paymentMethod, info);
        incomeRecordsTotal++;
    }

    public void addExpenditure(LocalDateTime date, BigDecimal sum, TransactionCategory category, PaymentMethod paymentMethod, String info) {
        expenses[expenseRecordsTotal] = new ExpenditureRecord(date, sum, category, paymentMethod, info);
        expenseRecordsTotal++;
    }

    public String listAllIncome() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < incomeRecordsTotal; i++) {
            result.append((i) + ". " + income[i].toString() + "\n");
        }
        return result.toString();
    }

    public String listAllExpenses() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < expenseRecordsTotal; i++) {
            result.append((i) + ". " + expenses[i].toString() + "\n");
        }
        return result.toString();
    }

    public String info() {
        BigDecimal incomeTotal = totalSumOfArray(income);
        BigDecimal expenseTotal = totalSumOfArray(expenses);
        BigDecimal balance = incomeTotal.subtract(expenseTotal);

        String result = String.format("Balance = " + balance + "\n" + "Total income = " + incomeTotal.toString() + "\n" + "Total expenses = " + expenseTotal.toString());
        return result;
    }

    public BigDecimal totalSumOfArray(RecordAbstract[] recordArray) {
        int max = 0;
        RecordAbstract[] ra = recordArray;
        if (recordArray instanceof IncomeRecord[]) {
            max = incomeRecordsTotal;
        } else if (recordArray instanceof ExpenditureRecord[]) {
            max = expenseRecordsTotal;
        }

        BigDecimal result = new BigDecimal("0");
        for (int i = 0; i < max; i++) {
            result = result.add(recordArray[i].getSum());
        }
        return result;
    }
}
