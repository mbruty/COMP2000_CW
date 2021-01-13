package com.view;

import com.KeyValuePair;
import com.controller.AbstractController;
import com.payment.AmericanExpress;
import com.payment.MasterCard;
import com.payment.PaymentContext;
import com.payment.Visa;
import com.receipt.ReceiptSingleton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

// As this is a simple form with very little functionality and doesn't have a model, it is not using the MVC pattern
public class CardPayment extends AbstractView {
  private JTextField cardTextField;
  private JButton submitButton;
  private JButton cancelButton;
  private JLabel amountDueLabel;
  private JLabel errorText;
  private JPanel mainPanel;
  private ArrayList<AbstractView> listeners;

  public CardPayment(float price) {
    this.setContentPane(mainPanel);
    this.listeners = new ArrayList<>();
    cancelButton.addActionListener( e -> this.dispose());
    submitButton.addActionListener(
            e -> {
              boolean result = false;
              switch (processCard(cardTextField.getText())) {
                case VISA -> {
                  Visa visa = new Visa();
                  result = visa.runPayment(price);
                  ReceiptSingleton.getInstance().getBuilder().setCardType(PaymentContext.cardType.VISA);
                }
                case MASTER_CARD -> {
                  MasterCard masterCard = new MasterCard();
                  result = masterCard.runPayment(price);
                  ReceiptSingleton.getInstance().getBuilder().setCardType(PaymentContext.cardType.MASTER_CARD);

                }
                case AMERICAN_EXPRESS -> {
                  AmericanExpress americanExpress = new AmericanExpress();
                  result = americanExpress.runPayment(price);
                  ReceiptSingleton.getInstance().getBuilder().setCardType(PaymentContext.cardType.AMERICAN_EXPRESS);

                }
                default -> errorText.setText("Invalid card");
              }
              if(result) {
                ReceiptSingleton.getInstance().getBuilder().setIsUsingCard();
                for (AbstractView view:
                     listeners) {
                  view.update(new KeyValuePair<Boolean>("TransactionComplete", true));
                  this.dispose();
                }
              }
              else errorText.setText("Error processing payment");

            }
    );
    this.initalise();
    // Round the float to 2 decimal places and set the amount due text to that
    this.setVisible(true);
    this.setPreferredSize(new Dimension(350, 200));

    amountDueLabel.setText("Amount due: " + Math.round(price * 100f) / 100f );
  }

  private static PaymentContext.cardType processCard(String cardNumber) {
    cardNumber = cardNumber.replaceAll("-", "");
    // Regex strings to match different card providers
    // Sourced from https://ihateregex.io/expr/credit-card/
    String visaRegex = "^4[0-9]{12}(?:[0-9]{3})$";
    String masterCardRegex = "^5[1-5][0-9]{14}$";
    String americanExpressRegex = "^3[47][0-9]{13}$";
    // Check match the regex and return the correct type
    if(cardNumber.matches(visaRegex)) return PaymentContext.cardType.VISA;
    if(cardNumber.matches(masterCardRegex)) return PaymentContext.cardType.MASTER_CARD;
    if(cardNumber.matches(americanExpressRegex)) return PaymentContext.cardType.AMERICAN_EXPRESS;
    return PaymentContext.cardType.INVALID;
  }

  // This view won't be updated
  @Override
  public void update(KeyValuePair item) {

  }

  // This view does not use a controller
  @Override
  public void setController(AbstractController controller) {

  }

  public void addActionListener(AbstractView view) {
    this.listeners.add(view);
  }
}
