package com.company.core.transactions;

import com.company.core.abstracts.RecordAbstract;
import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;
import com.company.core.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExpenditureRecord extends RecordAbstract {

    public ExpenditureRecord(int localId, LocalDateTime dateTime, BigDecimal sum, TransactionCategory transactionCategory, PaymentMethod paymentMethod, String additionalInfo) {
        super(localId, dateTime, sum, transactionCategory, paymentMethod,TransactionType.EXPENDITURE,  additionalInfo);
    }

    @Override
    public String toString() {
        return "Expense: " + super.toString();
    }
}
