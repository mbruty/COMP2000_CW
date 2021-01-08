package com.view;

import com.KeyValuePair;
import com.controller.AbstractController;
import com.controller.MultiAdminController;

import javax.swing.*;
import java.util.Arrays;

public class StartupForm extends AbstractView {
  private JPanel mainPanel;
  private JButton adminButton;
  private JButton pointOfSalesButton;
  private JPasswordField passwordField;
  private JTextField userNameTf;
  private JLabel errorTxt;
  private MultiAdminController controller;
  private AbstractView admin;

  public StartupForm(AbstractView admin, AbstractView pointOfSale) {
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.admin = admin;
    this.setContentPane(mainPanel);
    this.pack();
    this.center();
    this.setVisible(true);
    adminButton.addActionListener(e -> {
      controller.checkLogin(userNameTf.getText(), new String( passwordField.getPassword()));
    });
    pointOfSalesButton.addActionListener(e -> {
      pointOfSale.setVisible(true);
      this.setVisible(false);
    });
  }
  @Override
  public void update(KeyValuePair item) {
    if ("Validate".equals(item.key)) {
      if (item.value.equals("Validated")) {
        admin.setVisible(true);
        this.setVisible(false);
      } else {
        errorTxt.setText((String) item.value);
      }
    }
  }

  @Override
  public void setController(AbstractController controller) {
    this.controller = (MultiAdminController) controller;
  }
}
