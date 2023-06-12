package main.java.model;

import java.util.Objects;

public class Transaction {

  private final String name;

  private final String category;

  private final Result result;

  public Transaction(String name, String category, Result result) {
    this.name = name;
    this.category = category;
    this.result = result;
  }

  public String getName() {
    return name;
  }

  public String getCategory() {
    return category;
  }

  public Result getResult() {
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction that = (Transaction) o;
    return Objects.equals(name, that.name) && Objects.equals(category, that.category) && result == that.result;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, category, result);
  }

  @Override
  public String toString() {
    return "Transaction{" +
        "name='" + name + '\'' +
        ", category='" + category + '\'' +
        ", result=" + result +
        '}';
  }
}