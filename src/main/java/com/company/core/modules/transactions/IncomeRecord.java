package com.company.core.modules.transactions;

import com.company.core.enums.IncomeType;
import com.company.core.modules.RecordAbstract;
import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;
import com.company.core.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class IncomeRecord extends RecordAbstract {

    private IncomeType incomeType;

    public IncomeRecord(int localId,
                        LocalDateTime dateTime,
                        BigDecimal sum,
                        TransactionCategory transactionCategory,
                        PaymentMethod paymentMethod, IncomeType incomeType,
                        String additionalInfo) {
        super(localId, dateTime, sum, transactionCategory, paymentMethod, TransactionType.INCOME, additionalInfo);
        this.incomeType = incomeType;
    }

    @Override
    public String toString() {
        return "Income: " + super.toString();
    }
}
