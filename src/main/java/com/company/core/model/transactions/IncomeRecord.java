package com.company.core.model.transactions;

import com.company.core.enums.IncomeType;
import com.company.core.model.RecordAbstract;
import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;
import com.company.core.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class IncomeRecord extends RecordAbstract {


    private IncomeType incomeType;

    public IncomeType getIncomeType() {
        return incomeType;
    }

    /**
     * constructor with local id option
     * @param localId
     * @param dateTime
     * @param sum
     * @param transactionCategory
     * @param paymentMethod
     * @param incomeType
     * @param additionalInfo
     */
    public IncomeRecord(int localId,
                        LocalDateTime dateTime,
                        BigDecimal sum,
                        TransactionCategory transactionCategory,
                        PaymentMethod paymentMethod, IncomeType incomeType,
                        String additionalInfo) {
        super(localId, dateTime, sum, transactionCategory, paymentMethod, TransactionType.INCOME, additionalInfo);
        this.incomeType = incomeType;
    }

    /**
     * constructor without local id option
     * @param dateTime
     * @param sum
     * @param transactionCategory
     * @param paymentMethod
     * @param transactionType
     * @param additionalInfo
     * @param incomeType
     */
    public IncomeRecord(LocalDateTime dateTime,
                        BigDecimal sum,
                        TransactionCategory transactionCategory,
                        PaymentMethod paymentMethod,
                        TransactionType transactionType,
                        IncomeType incomeType,
                        String additionalInfo) {
        super(dateTime, sum, transactionCategory, paymentMethod, transactionType, additionalInfo);
        this.incomeType = incomeType;
    }

    @Override
    public String toString() {
        return "Income: " + super.toString();
    }

    /**
     * returns csv line of record
     * @return
     */
    @Override
    public String toCsvString() {

        StringBuilder sb = new StringBuilder();
        sb.append(super.getLocalId() + ",");
        sb.append(super.getDateTime().toString()).append(",");
        sb.append(super.getSum().toString()).append(",");
        sb.append(super.getTransactionCategory().toString()).append(",");
        sb.append(super.getPaymentMethod().toString()).append(",");
        sb.append(super.getTransactionType().toString()).append(",");
        sb.append(this.getIncomeType().toString()+",");
        sb.append(super.getAdditionalInfo());

        return sb.toString();
    }
}
