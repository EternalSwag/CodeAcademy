package com.company.core.transactions;

import com.company.core.abstracts.RecordAbstract;
import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;
import com.company.core.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class IncomeRecord extends RecordAbstract {

    public IncomeRecord(LocalDateTime dateTime, BigDecimal sum, TransactionCategory transactionCategory, PaymentMethod paymentMethod, String additionalInfo) {
        super(dateTime, sum, transactionCategory, paymentMethod, additionalInfo);
    }

    @Override
    public String toString() {
        return "Income: " + super.toString();
    }
}
