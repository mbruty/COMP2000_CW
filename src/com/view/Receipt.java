package com.view;

import com.KeyValuePair;
import com.controller.AbstractController;
import com.receipt.ReceiptSingleton;

import javax.swing.*;
import java.awt.*;

public class Receipt extends AbstractView {
  private JTextArea textArea1;
  private JPanel mainPannel;
  private JButton closeButton;

  public Receipt() {
    this.setContentPane(mainPannel);
    this.setPreferredSize(new Dimension(500, 600));
    this.initalise();
    this.setVisible(true);
    closeButton.addActionListener( e -> {
      this.dispose();
    });
    new SwingWorker<>() {
      @Override
      protected Object doInBackground() {

        textArea1.setText(ReceiptSingleton.getInstance().getBuilder().build().print());
        return null;
      }
    }.execute();
  }

  @Override
  public void update(KeyValuePair item) {

  }

  @Override
  public void setController(AbstractController controller) {

  }
}
