package com.payment;

public class MasterCard extends PaymentContext{
  @Override
  protected void hook() {
    // Contact master card to process payment here
    this.transactionSuccess = true;
  }
}
