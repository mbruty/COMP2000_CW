package com.view;

import com.KeyValuePair;
import com.Utils.ObjectToArray;
import com.controller.AbstractController;
import com.controller.StockController;

import javax.swing.*;
import java.util.Arrays;
import java.util.Objects;

public class Admin extends AbstractView {
  private JPanel mainPanel;
  private JList<String> productList;
  private JTextField nameTf;
  private JButton createNewButton;
  private JButton saveChangesButton;
  private JSpinner codeSpinner;
  private JSpinner priceSpinner;
  private JLabel currencyLbl;
  private JSpinner quantitySpinner;
  private JComboBox sortOptions;
  private JLabel valueLabel;
  private StockController controller;

  public Admin() {
    sortOptions.addActionListener(
            e -> {
              controller.sortModels((String) Objects.requireNonNull(sortOptions.getSelectedItem()));
            }
    );
    nameTf.addActionListener(
            e -> {
              controller.setModelProperty(new KeyValuePair<String>(AbstractController.NAME, nameTf.getText()));
            }
    );
    productList.addListSelectionListener(
            e -> {
              if (!e.getValueIsAdjusting()) {
                controller.swapModel(productList.getSelectedIndex());
              }
            }
    );
    priceSpinner.addChangeListener(
            e -> {
              controller.setModelProperty(new KeyValuePair<Float>(
                      AbstractController.PRICE,
                      priceSpinner.getValue()
              ));
            }
    );
    quantitySpinner.addChangeListener(
            e -> {
              controller.setModelProperty(new KeyValuePair<Integer>(
                      AbstractController.QUANTITY,
                      quantitySpinner.getValue()
              ));
            }
    );
    codeSpinner.addChangeListener(
            e -> {
              controller.setModelProperty(new KeyValuePair<Integer>(
                      AbstractController.CODE,
                      codeSpinner.getValue()
              ));
            }
    );
//    createNewButton.addActionListener(
//            e -> {
//              controller.
//            }
//    );
    priceSpinner.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 0.01));
    SpinnerNumberModel intSpinner = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
    codeSpinner.setModel(intSpinner);
    quantitySpinner.setModel(intSpinner);
    this.setContentPane(mainPanel);
    this.initalise();
  }

  public void setListOptions(String[] items) {
    productList.setListData(items);
    productList.setSelectedIndex(0);
  }

  public void update(KeyValuePair item) {
    System.out.println(item.value);
    switch (item.key) {
      case AbstractController.NAME -> {
        Object[] array =  ObjectToArray.convertToObjectArray(item.value);
        String[] names = Arrays.copyOf(array, array.length, String[].class);
        productList.setListData((String[]) names);
        nameTf.setText((String) names[Math.max(productList.getSelectedIndex(), 0)]);
      }
      case AbstractController.PRICE -> priceSpinner.setValue(item.value);
      case AbstractController.CODE -> codeSpinner.setValue(item.value);
      case AbstractController.QUANTITY -> quantitySpinner.setValue(item.value);
    }
  }

  @Override
  public void setController(AbstractController controller) {
    this.controller = (StockController) controller;
  }
}
