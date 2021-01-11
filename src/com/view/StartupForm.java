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
  private final Admin admin;

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
    });
  }
  @Override
  public void update(KeyValuePair item) {
    switch (item.key) {
      case "Validate" -> errorTxt.setText((String) item.value);
      case "Validated" -> {
        AdminController newController = (AdminController) item.value;
        newController.setView(admin);
        admin.setAdminController(newController);
        this.setVisible(false);
      }
      case "isAdmin" -> admin.setNotAdmin();
      case "Validated admin" -> {
        MultiAdminController newMultiController = (MultiAdminController) item.value;
        newMultiController.setView(admin);
        admin.setMultiAdminController((newMultiController));
        newMultiController.setupModel();
      }
    }
  }

  @Override
  public void setController(AbstractController controller) {
    this.controller = (MultiAdminController) controller;
  }
}
