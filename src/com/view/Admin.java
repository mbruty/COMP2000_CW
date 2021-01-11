package com.view;

import com.KeyValuePair;
import com.Utils.ObjectToArray;
import com.controller.AbstractController;
import com.controller.AdminController;
import com.controller.MultiAdminController;
import com.controller.StockController;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Objects;

public class Admin extends AbstractView {
  private JPanel mainPanel;
  private JTabbedPane tabbedPannel;
  private JList<String> productList;
  private JTextField nameTf;
  private JButton createNewButton;
  private JButton saveChangesButton;
  private JSpinner codeSpinner;
  private JSpinner priceSpinner;
  private JSpinner quantitySpinner;
  private JComboBox sortOptions;
  private JLabel fileStatusLbl;
  private JPanel userPanel;
  private JPanel stockPanel;
  private JTextField userNameTf;
  private JPasswordField passwordField;
  private JCheckBox administratorCheckBox;
  private JButton saveChangesButton1;
  private JButton deleteUserButton;
  private JList usersList;
  private JLabel adminSaveStatus;
  private JLabel valueLabel;
  private StockController controller;
  private AdminController adminController;
  private MultiAdminController multiAdminController;

  public Admin() {
    sortOptions.addActionListener(
            e -> {
              controller.sortModels((String) Objects.requireNonNull(sortOptions.getSelectedItem()));
            }
    );
    saveChangesButton.addActionListener(
            e -> {
              controller.writeFile();
            }
    );
    saveChangesButton1.addActionListener(
            e -> {
              multiAdminController.writeFile();
            }
    );
    administratorCheckBox.addActionListener(
            e -> {
              multiAdminController.setModelProperty(new KeyValuePair<Boolean>(MultiAdminController.IS_ADMIN, administratorCheckBox.isSelected()));
            }
    );
    nameTf.addActionListener(
            e -> {
                controller.setModelProperty(new KeyValuePair<String>(StockController.NAME, nameTf.getText()));
            }
    );
    productList.addListSelectionListener(
            e -> {
              if (!e.getValueIsAdjusting() && this.controller != null) {
                controller.swapModel(productList.getSelectedIndex());
              }
            }
    );
    priceSpinner.addChangeListener(
            e -> {
              controller.setModelProperty(new KeyValuePair<Float>(
                      StockController.PRICE,
                      priceSpinner.getValue()
              ));
            }
    );
    quantitySpinner.addChangeListener(
            e -> {
              controller.setModelProperty(new KeyValuePair<Integer>(
                      StockController.QUANTITY,
                      quantitySpinner.getValue()
              ));
            }
    );
    codeSpinner.addChangeListener(
            e -> {
              controller.setModelProperty(new KeyValuePair<Integer>(
                      StockController.CODE,
                      codeSpinner.getValue()
              ));
            }
    );
    createNewButton.addActionListener(
            e -> {
              controller.newModel();
            }
    );
    usersList.addListSelectionListener(e -> {
              multiAdminController.swapModel(usersList.getSelectedIndex());
            }
    );
    userNameTf.addActionListener(
            e -> {
              multiAdminController.setModelProperty(new KeyValuePair<String>(MultiAdminController.USER_NAME, userNameTf.getText()));
            }
    );
    passwordField.addActionListener(
            e -> {
              multiAdminController.setModelProperty(new KeyValuePair<String>(MultiAdminController.PASSWORD, new String(passwordField.getPassword())));
            }
    );
    priceSpinner.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 0.01));
    SpinnerNumberModel codeModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
    SpinnerNumberModel quantityModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);

    codeSpinner.setModel(codeModel);
    quantitySpinner.setModel(quantityModel);
    this.setContentPane(mainPanel);
    this.initalise();
  }

  public void setListOptions(String[] items) {
    productList.setListData(items);
    productList.setSelectedIndex(0);
  }

  public void update(KeyValuePair item) {
    switch (item.key) {
      case StockController.NAMES -> {
        Object[] array =  ObjectToArray.convertToObjectArray(item.value);
        String[] names = Arrays.copyOf(array, array.length, String[].class);
        productList.setListData(names);
        nameTf.setText(names[Math.max(productList.getSelectedIndex(), 0)]);
      }
      case StockController.NAME -> nameTf.setText((String)item.value);
      case StockController.PRICE -> priceSpinner.setValue(item.value);
      case StockController.CODE -> codeSpinner.setValue(item.value);
      case StockController.QUANTITY -> quantitySpinner.setValue(item.value);
      case "FileWrite" -> fileStatusLbl.setText((String)item.value);
      case "AdminFileWrite" -> adminSaveStatus.setText((String)item.value);
      case MultiAdminController.USER_NAME -> userNameTf.setText((String)item.value);
      case MultiAdminController.IS_ADMIN -> administratorCheckBox.setSelected((Boolean)item.value);
      case MultiAdminController.USERS -> {
        Object[] array = ObjectToArray.convertToObjectArray(item.value);
        String[] users = Arrays.copyOf(array, array.length, String[].class);
        usersList.setListData(users);
        userNameTf.setText(users[Math.max(usersList.getSelectedIndex(), 0)]);

      }
    }
  }

  @Override
  public void setController(AbstractController controller) {
    this.controller = (StockController) controller;
  }
  public void setAdminController(AdminController controller) {
    this.setVisible(true);
    this.adminController = controller;
  }
  public void setNotAdmin() {
    tabbedPannel.removeTabAt(1);
  }
  public void setMultiAdminController(MultiAdminController controller) {
    this.multiAdminController = controller;
  }
}
