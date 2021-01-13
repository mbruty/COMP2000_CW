package com.receipt;

// Usually singletons aren't a good design pattern to use
// The main exception to this is for logging
// Here we're logging the transaction to the receipt
// The only difference to usual logging is we're logging to a string
public class ReceiptSingleton {
  private static ReceiptSingleton instance;
  private Receipt.ReceiptBuilder builder;
  private ReceiptSingleton() {
    this.builder = new Receipt.ReceiptBuilder();
  }

  public Receipt.ReceiptBuilder getBuilder() { return this.builder; }

  public static ReceiptSingleton getInstance() {
    if(instance == null) {
      instance = new ReceiptSingleton();
    }
    return instance;
  }
  public static void reset() {
    instance = null;
  }
}
