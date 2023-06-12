package main.java.service.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import main.java.model.Result;
import main.java.model.Transaction;

public class FileReaderUtil {

  // This is a private field of the TransactionReader class that represents the directory to read files from.
  private final File dir;

  // This is the constructor for the TransactionReader class.
  public FileReaderUtil() {
    // Set the directory path.
    String dirPath = "case";
    // Create a new File object for the directory.
    this.dir = new File(dirPath);
    // Print a message indicating which directory is being read from.
    System.out.println("Reading files from directory: " + dir.getAbsolutePath());
    // Check if the directory exists and is a directory.
    if (!dir.exists() || !dir.isDirectory()) {
      // Print an error message if the directory is invalid.
      System.out.println("Invalid directory: " + dirPath);
    }
  }

  /**
   * This method reads a file and returns a map of transactions contained within.
   *
   * @param fileName The name of the file to read.
   */
  public Map<String, LinkedList<Transaction>> readFile(String fileName) {
    // Create a new File object for the file.
    File file = new File(dir, fileName);
    // Print a message indicating which file is being read.
    System.out.println("Reading file: " + file.getName());
    // Create a new linked list to store the transactions.
    LinkedList<Transaction> transactionList = new LinkedList<>();
    //System.out.println("File contents: ");
    try (BufferedReader br = new BufferedReader(new java.io.FileReader(file))) {
      String line;
      // Read each line of the file.
      while ((line = br.readLine()) != null) {
        //System.out.println(line);
        // Split the line into fields using a comma as the delimiter.
        String[] fields = line.split(",");
        // Check if there are 3 fields in the line.
        if (fields.length == 3) {
          // Create a new transaction object and add it to the list of transactions.
          Transaction transaction = new Transaction(fields[0].trim(),
              fields[1].trim(),
              Result.valueOf(fields[2].trim()));
          transactionList.add(transaction);
        }
      }
    } catch (IOException e) {
      // Print a stack trace if an error occurs while reading the file.
      e.printStackTrace();
    }
    // Create a new linked hash map to store the map of transactions.
    LinkedHashMap<String, LinkedList<Transaction>> fileTransactionMap = new LinkedHashMap<>(1);
    // Add the transaction list to the map of transactions, with the file name as the key.
    fileTransactionMap.put(file.getName(), transactionList);
    // Return the map of transactions.
    return fileTransactionMap;
  }


  /**
   * This method reads all the files in the directory and returns a map of transactions contained within.
   *
   * @param alreadyProcessedFiles The names of files that have already been processed.
   *                              If this is a set containing the names of all files in the directory, no files will be processed.
   */
  public Map<String, LinkedList<Transaction>> readFiles(Collection<String> alreadyProcessedFiles) {
    // Get an array of the file names in the directory.
    String[] fileNames = dir.list();
    // If there are no files in the directory, print an error message and return an empty map.
    if (fileNames == null) {
      System.out.println("No files found in directory: " + dir.getName());
      return Collections.emptyMap();
    }
    // Convert the array of file names to a list and sort it.
    List<String> fileNamesSet = new LinkedList<>(Arrays.asList(fileNames));
    Collections.sort(fileNamesSet);
    // Remove the names of files that have already been processed.
    fileNamesSet.removeAll(alreadyProcessedFiles);
    // If there are no new files to process, return an empty map.
    if (fileNamesSet.isEmpty()) {
      return Collections.emptyMap();
    }
    // Print the number and names of the new files that have been detected.
    System.out.println("Detected new " + fileNamesSet.size() + " files: " + fileNamesSet);

    // Create a new linked hash map to store the map of transactions.
    LinkedHashMap<String, LinkedList<Transaction>> fileTransactionMap = new LinkedHashMap<>(fileNamesSet.size());

    // Read each file and add the transactions to the map of transactions.
    for (String fileName : fileNamesSet) {
      fileTransactionMap.putAll(readFile(fileName));
    }

    // Return the map of transactions.
    return fileTransactionMap;
  }
}