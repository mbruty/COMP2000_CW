package com.payment;

public abstract class PaymentContext {
  protected boolean transactionSuccess;
  // Identifier for the different card types
  protected float price;
  public enum cardType{
    MASTER_CARD,
    VISA,
    AMERICAN_EXPRESS,
    INVALID
  }
  public boolean runPayment(float price) {
    start(price);
    hook();
    return end();
  }

  protected void start(float price) {
    // Prepare the transaction, and possibly do currency conversion if needed
    this.price = (float) Math.round(price * 100.0) / 100.0f;
  }

  protected abstract void hook();

  protected boolean end() {
    return this.transactionSuccess;
  }
}
