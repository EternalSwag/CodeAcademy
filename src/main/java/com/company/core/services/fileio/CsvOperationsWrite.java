package com.company.core.services.fileio;

import com.company.core.model.RecordAbstract;
import com.company.core.repositories.TransactionRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class CsvOperationsWrite {

    /**
     * writes transaction repository into file
     *
     * @param transactionRepository repository to write
     * @param destinationFilename   destination file path
     * @throws Exception
     */
    public static void writeToFileTransactionRepository(TransactionRepository transactionRepository, String destinationFilename) throws Exception {
        BufferedWriter bufferedWriter = null;
        try {
            FileWriter fileWriter = new FileWriter(destinationFilename);
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            System.out.println("CsvOperationsWrite->writeToFileTransactionRepository: problem creating file " + destinationFilename);
        }

        if (bufferedWriter == null) {
            throw new Exception("CsvOperationsWrite->writeToFileTransactionRepository: error occured trying to write to file, failed to initiate buffer: " + destinationFilename);
        }

        try {
            for (RecordAbstract currentRecord : transactionRepository.getAllRecords()) {
                writeToBuffer(bufferedWriter, currentRecord.toCsvString());
                writeToBuffer(bufferedWriter, "\n");
            }
        } catch (IOException e) {
            throw new Exception("CsvOperationsWrite->writeToFileTransactionRepository: error trying to write to file");
        }

        bufferedWriter.close();
    }

    /**
     * write anything to buffer
     *
     * @param bufferedWriter
     * @param s
     * @param <T>
     * @throws IOException
     */
    private static <T> void writeToBuffer(BufferedWriter bufferedWriter, T s) throws IOException {
        bufferedWriter.write(s.toString());
    }
}
