package com.company.core.abstracts;

import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public abstract class RecordAbstract {

    private UUID globalId;
    private int localId;

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
        this.globalId = UUID.randomUUID();
    }

    public BigDecimal getSum() {
        return sum;
    }

    public TransactionCategory getTransactionCategory() {
        return transactionCategory;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = dateTime.format(formatter);

        return "id=" + localId + " " +
                formatDateTime +
                ", sum=" + sum +
                ", transactionCategory=" + transactionCategory +
                ", paymentMethod=" + paymentMethod +
                ", " + additionalInfo;

    }

}
