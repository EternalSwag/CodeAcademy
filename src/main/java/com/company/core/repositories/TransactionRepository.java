package com.company.core.repositories;

import com.company.core.enums.TransactionType;
import com.company.core.modules.RecordAbstract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionRepository {

    private List<RecordAbstract> transactions = new ArrayList<>();

    public List<RecordAbstract> getAllRecords() {
        return transactions;
    }

    //READ

    /**
     * filter transactions by transaction type
     *
     * @param transactionType
     * @return
     */
    public List<RecordAbstract> getRecordsByTransactionType(TransactionType transactionType) {
        return transactions.stream()
                .filter(e -> e.getTransactionType() == transactionType)
                .collect(Collectors.toList());
    }

    /**
     * return transaction by id
     *
     * @param id
     * @return
     */
    public RecordAbstract getRecordByLocalId(int id) {
        RecordAbstract matchingObject = transactions.stream().
                filter(p -> p.getLocalId() == id).
                findAny().orElse(null);
        return matchingObject;
    }
}
