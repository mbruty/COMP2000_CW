package com.payment;

public class AmericanExpress extends PaymentContext{
  @Override
  protected void hook() {
    // We would contact American Express to process the payment here
    this.transactionSuccess = true;
  }
}
