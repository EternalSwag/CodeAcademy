package com.company.core.services.fileio;

import com.company.config.Constants;
import com.company.core.enums.*;
import com.company.core.model.RecordAbstract;
import com.company.core.model.transactions.ExpenditureRecord;
import com.company.core.model.transactions.IncomeRecord;
import com.company.core.repositories.TransactionRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CsvOperationsRead {


    /**
     * loads transaction from file
     * @param fileName source file path
     * @param firstLinesToSkip number of lines to skip (to ignore header)
     * @return
     * @throws Exception
     */
    public static TransactionRepository getTransactionListFromFile(String fileName, int firstLinesToSkip) throws Exception {

        TransactionRepository transactionRepository = new TransactionRepository();

        File myFile = new File(fileName);
        FileInputStream fis = new FileInputStream(myFile);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (firstLinesToSkip <= 0) {
                    RecordAbstract currentRecord = makeRecordFromCSVLine(line);
                    if (currentRecord != null) {
                        transactionRepository.createTransaction(currentRecord);
                    }
                }
                firstLinesToSkip--;
            }
        }
        return transactionRepository;
    }


    /**
     * creates transaction record by reading provided line
     * @param line string of line
     * @return record, based on type is it income or expenditure
     * @throws Exception
     */
    public static RecordAbstract makeRecordFromCSVLine(String line) throws Exception {
        String[] separatedWords = processCSVLine(line, Constants.CSV_FILE_SEPARATING_SYMBOL);
        if (separatedWords.length != 8) {
            throw new Exception("Record list has invalid entry, line " + line);
        }

        int localId = Integer.parseInt(separatedWords[0]);
        LocalDateTime date = LocalDateTime.parse(separatedWords[1]);
        BigDecimal sum = new BigDecimal(separatedWords[2]);
        TransactionCategory transactionCategory = TransactionCategory.valueOf(separatedWords[3]);
        PaymentMethod paymentMethod = PaymentMethod.valueOf(separatedWords[4]);
        TransactionType transactionType = TransactionType.valueOf(separatedWords[5]);
        String additionalInfo = separatedWords[7];

        RecordAbstract resultRecord;

        switch (separatedWords[5]) {
            case "INCOME":
                IncomeType incomeType = IncomeType.valueOf(separatedWords[6]);
                resultRecord = new IncomeRecord(localId, date, sum, transactionCategory, paymentMethod, incomeType, additionalInfo);
                break;
            case "EXPENDITURE":
                ExpenditureType expenditureType = ExpenditureType.valueOf(separatedWords[6]);
                resultRecord = new ExpenditureRecord(localId, date, sum, transactionCategory, paymentMethod, expenditureType, additionalInfo);
                break;
            default:
                throw new Exception("CsvOperations->makeRecordFromCSVLine: uknown type of transaction, line " + line);
        }

        return resultRecord;
    }

    private static String[] processCSVLine(String line, String separatorSymbol) {
        line = initialCSVLineProcess(line);
        String[] separatedWords = splitterLineCSV(line, separatorSymbol);
        return separatedWords;
    }

    /**
     * trim, remove spaces
     *
     * @return
     */
    private static String initialCSVLineProcess(String rawCSVLine) {
        String result = rawCSVLine.trim();
        result = result.replaceAll("\\s+", "");
        return result;
    }

    /**
     * split line by separating symbol, ex ","
     *
     * @param line
     * @param separatorSymbol
     * @return
     */
    private static String[] splitterLineCSV(String line, String separatorSymbol) {
        String[] result = line.split(separatorSymbol);
        return result;
    }
}
