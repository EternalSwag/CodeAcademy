package com.company.core.enums;

public enum TransactionCategory {
    ASSET(1),
    LIABILITY(2),
    EQUITY(3),
    REVENUE(4),
    EXPENSES(5);

    TransactionCategory(final int transactionCode) {
        this.transactionCode = transactionCode;
    }

    private final int transactionCode;

    public int getTransactionCode() {
        return transactionCode;
    }
}

