package com.view;

import com.KeyValuePair;
import com.controller.AbstractController;

import javax.swing.*;
import java.awt.*;

public class StartupForm extends AbstractView {
  private JPanel mainPanel;
  private JButton adminButton;
  private JButton pointOfSalesButton;
  private JPasswordField passwordField1;
  private JTextField textField1;
  private AbstractController controller;

  public StartupForm(AbstractView admin, AbstractView pointOfSale) {
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setContentPane(mainPanel);
    this.pack();
    this.center();
    this.setVisible(true);
    adminButton.addActionListener(e -> {
      admin.setVisible(true);
      this.setVisible(false);
    });
    pointOfSalesButton.addActionListener(e -> {
      pointOfSale.setVisible(true);
      this.setVisible(false);
    });
  }

  @Override
  public void update(KeyValuePair item) {
  }

  @Override
  public void setController(AbstractController controller) {
    this.controller = controller;
  }
}
