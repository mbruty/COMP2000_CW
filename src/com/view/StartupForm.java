package com.view;

import com.KeyValuePair;
import com.controller.AbstractController;
import com.controller.AdminController;
import com.controller.MultiAdminController;

import javax.swing.*;

public class StartupForm extends AbstractView {
  private JPanel mainPanel;
  private JButton adminButton;
  private JButton pointOfSalesButton;
  private JPasswordField passwordField;
  private JTextField userNameTf;
  private JLabel errorTxt;
  private MultiAdminController controller;
  private Admin admin;

  public StartupForm(Admin admin, AbstractView pointOfSale) {
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.admin = admin;
    this.setContentPane(mainPanel);
    this.pack();
    this.center();
    this.setVisible(true);
    adminButton.addActionListener(e -> {
      new SwingWorker<>() {

        @Override
        protected Object doInBackground() throws Exception {
          controller.checkLogin(userNameTf.getText(), new String( passwordField.getPassword()));
          return null;
        }
      }.execute();
    });
    pointOfSalesButton.addActionListener(e -> {
      pointOfSale.setVisible(true);
      this.setVisible(false);
    });
  }
  @Override
  public void update(KeyValuePair item) {
    if (item.key.equals("Validate")) {
      errorTxt.setText((String) item.value);
    } else if (item.key.equals("Validated")) {
      AdminController newController = (AdminController) item.value;
      newController.setView(admin);
      admin.setAdminController(newController);
    } else if (item.key.equals("isAdmin")) {
      admin.setNotAdmin();
    }
  }

  @Override
  public void setController(AbstractController controller) {
    this.controller = (MultiAdminController) controller;
  }
}
