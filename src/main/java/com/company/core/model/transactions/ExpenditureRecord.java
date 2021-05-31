package com.company.core.modules.transactions;

import com.company.core.enums.ExpenditureType;
import com.company.core.modules.RecordAbstract;
import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;
import com.company.core.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExpenditureRecord extends RecordAbstract {

    private ExpenditureType expenditureType;

    public ExpenditureRecord(int localId,
                             LocalDateTime dateTime,
                             BigDecimal sum,
                             TransactionCategory transactionCategory,
                             PaymentMethod paymentMethod, ExpenditureType expenditureType,
                             String additionalInfo) {
        super(localId, dateTime, sum, transactionCategory, paymentMethod, TransactionType.EXPENDITURE, additionalInfo);
        this.expenditureType = expenditureType;
    }

    public void setExpenditureType(ExpenditureType expenditureType) {
        this.expenditureType = expenditureType;
    }

    @Override
    public String toString() {
        return "Expense: " + super.toString();
    }
}
