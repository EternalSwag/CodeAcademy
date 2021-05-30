package com.company.core.enums;

public enum TransactionType {
    INCOME (1),
    EXPENDITURE(2);

    TransactionType(final int transactionCode) {
        this.transactionType = transactionCode;
    }

    private final int transactionType;

    public int getTransactionCode() {
        return transactionType;
    }
    }
