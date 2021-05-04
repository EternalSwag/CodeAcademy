package com.company.core.transactions;

import com.company.core.abstracts.RecordAbstract;
import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;
import com.company.core.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExpenditureRecord extends RecordAbstract {

    public ExpenditureRecord(LocalDateTime dateTime, BigDecimal sum, TransactionCategory transactionCategory, PaymentMethod paymentMethod, String additionalInfo) {
        super(dateTime, sum, transactionCategory, paymentMethod, additionalInfo);
    }

}
