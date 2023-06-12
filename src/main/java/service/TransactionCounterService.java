package main.java.service;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.java.model.Result;
import main.java.model.Transaction;

public class TransactionCounterService {
  public TransactionCounterService() {

  }

  public void process(List<Transaction> transactionList) {
    // Map to store transaction counts categorized by category and result
    Map<String, EnumMap<Result, Integer>> transactionCountMap = new HashMap<>();

    // Iterate over each transaction in the list
    for (Transaction transaction : transactionList) {
      String category = transaction.getCategory();
      Result result = transaction.getResult();

      // Check if the category is already present in the map
      if (transactionCountMap.containsKey(category)) {
        EnumMap<Result, Integer> resultCountMap = transactionCountMap.get(category);
        // Check if the result is already present in the category's result count map
        if (resultCountMap.containsKey(result)) {
          // If result is already present, increment its count by 1
          resultCountMap.put(result, resultCountMap.get(result) + 1);
        } else {
          // If result is not present, add it with count 1
          resultCountMap.put(result, 1);
        }
      } else {
        // If category is not present, create a new result count map for the category
        EnumMap<Result, Integer> resultCountMap = new EnumMap<>(Result.class);
        resultCountMap.put(result, 1);
        transactionCountMap.put(category, resultCountMap);
      }
    }

    // Write the transaction counts to output
    write(transactionCountMap);
  }

  private void write(Map<String, EnumMap<Result, Integer>> transactionCountMap) {
    // Iterate over the transaction count map
    for (Map.Entry<String, EnumMap<Result, Integer>> entry : transactionCountMap.entrySet()) {
      StringBuilder stringBuilder = new StringBuilder(entry.getKey() + ": ");
      EnumMap<Result, Integer> resultCountMap = entry.getValue();

      // Iterate over the possible result values
      for (Result result : Result.values()) {
        // Append the result name and its count to the output string
        stringBuilder.append(result).append(" = ").append(resultCountMap.getOrDefault(result, 0)).append(", ");
      }

      // Print the output string for the current category
      System.out.println(stringBuilder);
    }
  }
}