package com.receipt;

import com.Main;
import com.Utils.ChangeToString;
import com.model.ItemModel;
import com.payment.PaymentContext;

import java.util.Date;
import java.util.List;

public class Receipt {
  private final boolean isCard;
  private final boolean isCash;
  private final PaymentContext.cardType cardType;
  private final float price;
  private final List<ItemModel> itemsBought;
  private final float amountPaid;
  private final List<Integer>  changeGiven;

  public Receipt(ReceiptBuilder builder) {
    this.isCard = builder.getIsCard();
    this.isCash = builder.getIsCash();
    this.price = builder.getPrice();
    this.itemsBought = builder.getItemsBought();
    this.amountPaid = builder.getAmountPaid();
    this.changeGiven = builder.getChangeGiven();
    this.cardType = builder.getCardType();
  }

  public String print() {
    StringBuilder receipt = new StringBuilder();
    receipt.append(Main.COMPANY_NAME + "\n");
    // If it's using a card, add the card info
    if(isCard) {
      receipt.append("Payment Provider: ");
      receipt.append(cardType == PaymentContext.cardType.MASTER_CARD ? "Mastercard\n" : "");
      receipt.append(cardType == PaymentContext.cardType.VISA ? "Visa\n" : "");
      receipt.append(cardType == PaymentContext.cardType.AMERICAN_EXPRESS ? "American Express\n" : "");
    }

    receipt.append(new Date().toString()).append("\n");
    // Print every item
    for (ItemModel item:
         itemsBought) {
      // Print each item in the quantity
      for (int i = 0; i < item.getQuantity(); i++) {
        receipt.append(item.getName()).append(" - £").append(item.getPrice()).append("\n");
      }
    }
    // If it's using cash, add cash info
    if(isCash) {
      receipt.append("Cash Paid: ").append(amountPaid).append("\n");
      receipt.append("Dispensing Change: \n")
              .append(ChangeToString.convert(changeGiven))
              .append("\n");
    }
    receipt.append("Total: ").append(price);

    // Build the string and return it
    return receipt.toString();
  }

  public static class ReceiptBuilder {
    private boolean isCard = false;
    private boolean isCash = false;
    private PaymentContext.cardType cardType;
    private float price;
    private List<ItemModel> itemsBought;
    private float amountPaid;
    private List<Integer> changeGiven;

    public ReceiptBuilder() { }

    public ReceiptBuilder setPrice(float price) { this.price = price; return this; }

    public ReceiptBuilder setItemsBought(List<ItemModel> items) {
      this.itemsBought = items;
      return this;
    }

    public ReceiptBuilder setCardType(PaymentContext.cardType cardType) {
      this.cardType = cardType;
      return this;
    }

    // isCard defaults to false, so no need to use a boolean parameter here
    public ReceiptBuilder setIsUsingCard() {
      this.isCard = true;
      return this;
    }

    public ReceiptBuilder setIsUsingCash() {
      this.isCash = true;
      return this;
    }

    public ReceiptBuilder setAmountPaid(float amount) {
      this.amountPaid = amount;
      return this;
    }

    public ReceiptBuilder setChangeGiven(List<Integer> change) {
      this.changeGiven = change;
      return this;
    }


    public List<ItemModel> getItemsBought() {
      return itemsBought;
    }

    public float getPrice() {
      return price;
    }

    public boolean getIsCard() { return isCard; }
    public boolean getIsCash() { return isCash; }

    public float getAmountPaid() { return amountPaid; }
    public List<Integer>  getChangeGiven() { return changeGiven; }

    public PaymentContext.cardType getCardType() { return cardType; }

    public Receipt build() { return new Receipt(this); }
  }
}
