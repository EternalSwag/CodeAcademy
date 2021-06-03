package com.company.core.model;

import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;
import com.company.core.enums.TransactionType;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public abstract class RecordAbstract {

    private UUID globalId;
    private int localId;

    private LocalDateTime dateTime;
    private BigDecimal sum;
    private TransactionCategory transactionCategory;
    private PaymentMethod paymentMethod;
    private TransactionType transactionType;
    private String additionalInfo;

    public RecordAbstract(int localId, LocalDateTime dateTime, BigDecimal sum, TransactionCategory transactionCategory, PaymentMethod paymentMethod, TransactionType transactionType, String additionalInfo) {
        this.localId = localId;
        this.dateTime = dateTime;
        this.sum = sum;
        this.transactionCategory = transactionCategory;
        this.paymentMethod = paymentMethod;
        this.transactionType = transactionType;
        this.additionalInfo = additionalInfo;
        this.globalId = UUID.randomUUID();
    }

    public RecordAbstract(LocalDateTime dateTime, BigDecimal sum, TransactionCategory transactionCategory, PaymentMethod paymentMethod, TransactionType transactionType, String additionalInfo) {
        this.dateTime = dateTime;
        this.sum = sum;
        this.transactionCategory = transactionCategory;
        this.paymentMethod = paymentMethod;
        this.transactionType = transactionType;
        this.additionalInfo = additionalInfo;
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

    public UUID getGlobalId() {
        return globalId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * formats decimal to have 2 decimal numbers
     *
     * @param value
     * @return
     */
    private BigDecimal formatBigDecimal(BigDecimal value) {
        DecimalFormat df = new DecimalFormat("####.00");
        return new BigDecimal(df.format(value));
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = dateTime.format(formatter);


        return "id=" + localId + " " +
                formatDateTime +
                ", sum=" + formatBigDecimal(sum) +
                ", transactionCategory=" + transactionCategory +
                ", paymentMethod=" + paymentMethod +
                ", " + additionalInfo;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordAbstract that = (RecordAbstract) o;
        return Objects.equals(localId, that.localId);
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public abstract String toCsvString();
}
