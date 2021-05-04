package com.company.core.abstracts;

import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;
import com.company.core.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class RecordAbstract {

    private UUID id;
    private LocalDateTime dateTime;
    private BigDecimal sum;
    private TransactionCategory transactionCategory;
    private PaymentMethod paymentMethod;
    private String additionalInfo;

    public RecordAbstract(LocalDateTime dateTime, BigDecimal sum, TransactionCategory transactionCategory, PaymentMethod paymentMethod, String additionalInfo) {
        this.dateTime = dateTime;
        this.sum = sum;
        this.transactionCategory = transactionCategory;
        this.paymentMethod = paymentMethod;
        this.additionalInfo = additionalInfo;
    }
}
