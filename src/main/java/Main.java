package main.java;

import main.java.model.Transaction;
import main.java.service.TransactionCounterService;
import main.java.service.util.FileReaderUtil;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {

  public static void main(String[] args) {

    // Map to keep track of processed files
    List<String> processedFileNameList = new ArrayList<>();

    // Instantiate the TransactionCounterService and FileReader classes
    TransactionCounterService transactionCounterService = new TransactionCounterService();
    FileReaderUtil fileReader = new FileReaderUtil();

    // Loop continuously to listen to new files
    while (true) {

      // Get the list of new files that have not been processed yet
      Map<String, LinkedList<Transaction>> fileTransactionListMap = fileReader.readFiles(processedFileNameList);

      // If there are new files, process them and add them to the processed file map
      if (!fileTransactionListMap.isEmpty()) {
        // Loop through each file in the fileTransactionMap
        for (Map.Entry<String, LinkedList<Transaction>> fileTransaction : fileTransactionListMap.entrySet()) {
          System.out.println("--------------------------------------------------------------------------------");

          System.out.println("Processing new file: " + fileTransaction.getKey());

          // Process the file
          transactionCounterService.process(fileTransaction.getValue());

          // Add the file to the processed file map
          processedFileNameList.add(fileTransaction.getKey());

          System.out.println("--------------------------------------------------------------------------------");
        }
      }

      // Sleep for 3 seconds
      try {
        Thread.sleep(3);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

}