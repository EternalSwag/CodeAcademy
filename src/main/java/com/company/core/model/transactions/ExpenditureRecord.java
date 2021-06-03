package com.company.core.model.transactions;

import com.company.core.enums.ExpenditureType;
import com.company.core.model.RecordAbstract;
import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;
import com.company.core.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExpenditureRecord extends RecordAbstract {

    private ExpenditureType expenditureType;

    /**
     * constructor with local id option
     *
     * @param localId
     * @param dateTime
     * @param sum
     * @param transactionCategory
     * @param paymentMethod
     * @param expenditureType
     * @param additionalInfo
     */
    public ExpenditureRecord(int localId,
                             LocalDateTime dateTime,
                             BigDecimal sum,
                             TransactionCategory transactionCategory,
                             PaymentMethod paymentMethod, ExpenditureType expenditureType,
                             String additionalInfo) {
        super(localId, dateTime, sum, transactionCategory, paymentMethod, TransactionType.EXPENDITURE, additionalInfo);
        this.expenditureType = expenditureType;
    }

    /**
     * constructor without local id option
     *
     * @param dateTime
     * @param sum
     * @param transactionCategory
     * @param paymentMethod
     * @param transactionType
     * @param additionalInfo
     * @param expenditureType
     */
    public ExpenditureRecord(LocalDateTime dateTime,
                             BigDecimal sum,
                             TransactionCategory transactionCategory,
                             PaymentMethod paymentMethod,
                             TransactionType transactionType,
                             ExpenditureType expenditureType,
                             String additionalInfo) {
        super(dateTime, sum, transactionCategory, paymentMethod, transactionType, additionalInfo);
        this.expenditureType = expenditureType;
    }


    public void setExpenditureType(ExpenditureType expenditureType) {
        this.expenditureType = expenditureType;
    }

    public ExpenditureType getExpenditureType() {
        return expenditureType;
    }

    @Override
    public String toString() {
        return "Expense: " + super.toString();
    }

    @Override
    public String toCsvString() {
        StringBuilder sb = new StringBuilder();

        sb.append(super.getLocalId() + ",");
        sb.append(super.getDateTime().toString()).append(",");
        sb.append(super.getSum().toString()).append(",");
        sb.append(super.getTransactionCategory().toString()).append(",");
        sb.append(super.getPaymentMethod().toString()).append(",");
        sb.append(super.getTransactionType().toString()).append(",");
        sb.append(this.getExpenditureType().toString() + ",");
        sb.append(super.getAdditionalInfo());

        return sb.toString();
    }
}
