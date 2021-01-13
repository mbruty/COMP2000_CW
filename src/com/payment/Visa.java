package com.payment;

public class Visa extends PaymentContext{
  @Override
  protected void hook() {
    // We would contact Visa to process the payment here
    this.transactionSuccess = true;
  }
}
