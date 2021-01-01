package com.view;

import com.KeyValuePair;
import com.controller.AbstractController;

import javax.swing.*;

public class Admin extends AbstractView {
  private JPanel mainPanel;
  private JList<String> productList;
  private JTextField nameTf;
  private JTextField priceTf;
  private JButton createNewButton;
  private JButton saveChangesButton;
  private JTextField textField1;
  private JLabel valueLabel;
  private AbstractController controller;

  public Admin() {
    nameTf.addActionListener(
            e -> {
              controller.setModelProperty(new KeyValuePair<String>(AbstractController.NAME, priceTf.getText()));
            }
    );
    productList.addListSelectionListener(
            e -> {
              if(!e.getValueIsAdjusting()) {
                controller.swapModel(productList.getSelectedIndex());
              }
            }
    );
    this.setContentPane(mainPanel);
    this.initalise();
  }

  public void setListOptions(String[] items) {
    productList.setListData(items);
    productList.setSelectedIndex(0);
  }

  @Override
  public void update(KeyValuePair item) {
    switch(item.key) {
      case AbstractController.NAME ->
              nameTf.setText((String) item.value);
      case AbstractController.PRICE ->
              priceTf.setText(item.value.toString());
    }
  }

  @Override
  public void setController(AbstractController controller) {
    this.controller = controller;
  }
}
