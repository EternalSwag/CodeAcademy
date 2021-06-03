package com.company.core.services.fileio;

import java.util.List;

public interface CsvReadableWriteable<T> {



    /**
     * method to write data to csv
     *
     * @param destinationFileName file destination path
     * @return
     */
    String writeToCsv(String destinationFileName);

    /**
     * method to read data from csv and return it as list of objects of desirable type
     *
     * @param sourceFileName file path to read from
     * @return list of objects
     */
    List<T> readCSVData(String sourceFileName);
}
