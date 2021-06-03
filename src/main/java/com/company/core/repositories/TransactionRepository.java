package com.company.core.repositories;

import com.company.core.enums.TransactionType;
import com.company.core.model.RecordAbstract;
import com.company.core.services.fileio.CsvReadableWriteable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TransactionRepository implements CsvReadableWriteable {

    private List<RecordAbstract> transactions = new ArrayList<>();
    private int localIdCounter = 0;

    //CREATE

    /**
     * adds new transaction to repository
     *
     * @param record
     * @return global id (UUID)
     */
    public UUID createTransaction(RecordAbstract record) throws Exception {
        this.localIdCounter++;
        record.setLocalId(this.localIdCounter);
        transactions.add(record);
        return record.getGlobalId();
    }

    //READ

    /**
     * returns list of all transactions from repository
     *
     * @return
     */
    public List<RecordAbstract> getAllRecords() {
        return transactions;
    }

    /**
     * filters transactions by transaction type
     *
     * @param transactionType
     * @return list of transactions by provided criteria
     */
    public List<RecordAbstract> getRecordsByTransactionType(TransactionType transactionType) {
        return transactions.stream()
                .filter(e -> e.getTransactionType() == transactionType)
                .collect(Collectors.toList());
    }

    /**
     * return transaction by local transaction id
     *
     * @param localId
     * @return null if nothing was found
     */
    public RecordAbstract getRecordByLocalId(int localId) {
        RecordAbstract matchingObject = transactions.stream().
                filter(p -> p.getLocalId() == localId).
                findAny().orElse(null);
        return matchingObject;
    }

    //UPDATE

    /**
     * update transaction record by local id provided
     *
     * @param localId
     */
    public void updateRecord(int localId, RecordAbstract updatedTransaction) throws Exception {
        RecordAbstract oldTransaction = getRecordByLocalId(localId);
        if (!oldTransaction.equals(updatedTransaction))
            throw new Exception("TransactionRepository->UpdateRecord:" +
                    " transaction global id's doesn't match." +
                    " Provided: " + localId + ", trying to update: " + updatedTransaction.getLocalId());

        deleteRecordByLocalId(localId);
        transactions.add(updatedTransaction);
    }

    //DELETE

    /**
     * deletes transaction by local id provided
     *
     * @param localId
     * @throws Exception if it doesn't exist
     */
    public void deleteRecordByLocalId(int localId) throws Exception {
        RecordAbstract transactionToDelete = getRecordByLocalId(localId);
        if (transactionToDelete == null) throw new Exception("TransactionRepository->DeleteRecordByLocalId:" +
                " This transaction with id " + localId + " doesn't exist");
        transactions.remove(transactionToDelete);
    }

    /**
     * return last localId
     * @return
     */
    public int getLocalIdCounter() {
        return localIdCounter;
    }

    @Override
    public String writeToCsv(String destinationFileName) {

        return null;
    }

    @Override
    public List readCSVData(String sourceFileName) {
        return null;
    }
}
