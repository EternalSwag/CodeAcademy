package com.company.core.services;

import com.company.core.enums.TransactionType;
import com.company.core.modules.RecordAbstract;
import com.company.core.repositories.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

public class TransactionDataOutput {


    /**
     * returns all transactions (income, expense)
     *
     * @param transactionRepository
     * @return
     */
    public static List<String> listTransactions(TransactionRepository transactionRepository) {
        List<String> resultList = new ArrayList<>();
        List<RecordAbstract> transactionList = transactionRepository.getAllRecords();
        for (RecordAbstract ra : transactionList) {
            resultList.add(ra.toString());
        }
        return resultList;
    }

    /**
     * returns transactions by type (income, expense)
     *
     * @param transactionRepository
     * @param transactionType
     * @return
     */
    public static List<String> listTransactions(TransactionRepository transactionRepository, TransactionType transactionType) {
        List<String> resultList = new ArrayList<>();
        List<RecordAbstract> transactionList = transactionRepository.getRecordsByTransactionType(transactionType);
        for (RecordAbstract ra : transactionList) {
            resultList.add(ra.toString());
        }
        return resultList;
    }

    /**
     * returns list of transactions of all types, as single string
     *
     * @param transactionRepository
     * @return
     */
    public static String listTransactionsAsString(TransactionRepository transactionRepository) {
        StringBuilder sb = new StringBuilder();
        List<RecordAbstract> transactionList = transactionRepository.getAllRecords();
        for (RecordAbstract ra : transactionList) {
            sb.append(ra.toString());
            sb.append("\n");
        }
        return sb.toString();
    }


    /**
     * returns list of transactions of specific type, as single string
     *
     * @param transactionRepository
     * @param transactionType
     * @return
     */
    public static String listTransactionsAsString(TransactionRepository transactionRepository, TransactionType transactionType) {
        StringBuilder sb = new StringBuilder();
        List<RecordAbstract> transactionList = transactionRepository.getRecordsByTransactionType(transactionType);
        for (RecordAbstract ra : transactionList) {
            sb.append(ra.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
