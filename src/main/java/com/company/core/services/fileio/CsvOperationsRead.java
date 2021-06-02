package com.company.core.services.fileio;

import com.company.config.Constants;
import com.company.core.model.RecordAbstract;
import com.company.core.repositories.TransactionRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class CsvOperations {

    public TransactionRepository getTransactionListFromFile(String fileName, int firstLinesToSkip) throws Exception {

        TransactionRepository transactionRepository = new TransactionRepository();

        File myFile = new File(fileName);
        FileInputStream fis = new FileInputStream(myFile);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (firstLinesToSkip <= 0) {
                    RecordAbstract currentRecord = makeRecordFromCSVLine(line);
                    //todo
                    // if (currentRecord != null) transactionRepository.createTransaction()
                }
                firstLinesToSkip--;
            }
        }

        return transactionRepository;
    }


    public RecordAbstract makeRecordFromCSVLine(String line) throws Exception {
        String[] separatedWords = processCSVLine(line, Constants.CSV_FILE_SEPARATING_SYMBOL);
        if (separatedWords.length != 4) {
            throw new Exception("Payment list has invalid entry, line " + line);
        }

        int transactionId = 0;
        BigDecimal sum = null;
        int toId = 0;
        int fromId = 0;
        try {
            transactionId = Integer.valueOf(separatedWords[0]);
            sum = new BigDecimal(separatedWords[1]);
            toId = Integer.valueOf(separatedWords[2]);
            fromId = Integer.valueOf(separatedWords[3]);
        } catch (NumberFormatException e) {
            System.out.println("Format error processing line: " + line);
        }

        return null;
        // return new RecordAbstract(transactionId, sum, toId, fromId) {
    }


    private String[] processCSVLine(String line, String separatorSymbol) {
        line = initialCSVLineProcess(line);
        String[] separatedWords = splitterLineCSV(line, separatorSymbol);
        return separatedWords;
    }

    /**
     * trim, remove spaces
     *
     * @return
     */
    private String initialCSVLineProcess(String rawCSVLine) {
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
    private String[] splitterLineCSV(String line, String separatorSymbol) {
        String[] result = line.split(separatorSymbol);
        return result;
    }
}
