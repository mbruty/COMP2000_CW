package com.view;

import com.KeyValuePair;
import com.controller.AbstractController;
import com.payment.Cash;
import com.receipt.ReceiptSingleton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CashPayment extends AbstractView{
  private JButton £10Button;
  private JButton £20Button;
  private JButton £50Button;
  private JButton £5Button;
  private JButton £2Button;
  private JButton £1Button;
  private JButton a50pButton;
  private JButton a20pButton;
  private JButton a10pButton;
  private JButton a1pButton;
  private JButton a2pButton;
  private JButton a5pButton;
  private JButton submitButton;
  private JLabel priceLbl;
  private JPanel mainPanel;
  private final float due;
  private float currentPaid = 0f;
  private final String PAY = "pay";
  private ArrayList<AbstractView> listeners;

  public CashPayment(float due) {
    listeners = new ArrayList<>();
    this.due = due;
    this.setContentPane(mainPanel);
    £10Button.addActionListener( e -> update(new KeyValuePair<Float>(PAY, 10f)));
    £20Button.addActionListener( e -> update(new KeyValuePair<Float>(PAY, 20f)));
    £50Button.addActionListener( e -> update(new KeyValuePair<Float>(PAY, 50f)));
    £5Button.addActionListener( e -> update(new KeyValuePair<Float>(PAY, 5f)));
    £2Button.addActionListener( e -> update(new KeyValuePair<Float>(PAY, 2f)));
    £1Button.addActionListener( e -> update(new KeyValuePair<Float>(PAY, 1f)));
    a50pButton.addActionListener( e -> update(new KeyValuePair<Float>(PAY, 0.5f)));
    a20pButton.addActionListener( e -> update(new KeyValuePair<Float>(PAY, 0.2f)));
    a10pButton.addActionListener( e -> update(new KeyValuePair<Float>(PAY, 0.1f)));
    a1pButton.addActionListener( e -> update(new KeyValuePair<Float>(PAY, 0.01f)));
    a2pButton.addActionListener( e -> update(new KeyValuePair<Float>(PAY, 0.02f)));
    a5pButton.addActionListener( e -> update(new KeyValuePair<Float>(PAY, 0.05f)));
    submitButton.addActionListener( e -> {
      if(currentPaid < due) {
        priceLbl.setText("Not enough paid");
        return;
      }
      ReceiptSingleton.getInstance().getBuilder()
              .setIsUsingCash()
              .setAmountPaid(currentPaid)
              .setPrice(due);
      try {
        List<Integer> change = Cash.calcChange(due, currentPaid);
        ReceiptSingleton.getInstance().getBuilder().setChangeGiven(change);

      } catch (Exception exception) {
        exception.printStackTrace();
      }
      for (AbstractView listener:
           listeners) {
        listener.update(new KeyValuePair("TransactionComplete", true));
      }
      this.dispose();
    });
    this.setPreferredSize(new Dimension(450, 300));
    priceLbl.setText("£" + (due - currentPaid));
    this.initalise();
    this.setVisible(true);
  }

  @Override
  public void update(KeyValuePair item) {
    if(item.key.equals(PAY)) {
      currentPaid += (float) item.value;
      if(currentPaid < due) {
        priceLbl.setText("£" + (due - currentPaid));
      } else {
        priceLbl.setText("");
      }
    }
  }

  // This view doesn't use a controller
  @Override
  public void setController(AbstractController controller) {

  }

  public void addActionListener(AbstractView view) {
    this.listeners.add(view);
  }
}
