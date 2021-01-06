package com.view;

import com.KeyValuePair;
import com.Utils.ObjectToArray;
import com.controller.AbstractController;
import com.controller.StockController;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Objects;

public class Admin extends AbstractView {
  private JPanel mainPanel;
  private JTabbedPane tabbedPane1;
  private JList<String> productList;
  private JTextField nameTf;
  private JButton createNewButton;
  private JButton saveChangesButton;
  private JSpinner codeSpinner;
  private JSpinner priceSpinner;
  private JSpinner quantitySpinner;
  private JComboBox sortOptions;
  private JLabel fileStatusLbl;
  private JLabel valueLabel;
  private StockController controller;

  public Admin() {
    sortOptions.addActionListener(
            e -> {
              controller.sortModels((String) Objects.requireNonNull(sortOptions.getSelectedItem()));
            }
    );
    saveChangesButton.addActionListener(
            e -> {
              controller.writeToFile();
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
    createNewButton.addActionListener(
            e -> {
              controller.newModel();
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
      case AbstractController.NAMES -> {
        Object[] array =  ObjectToArray.convertToObjectArray(item.value);
        String[] names = Arrays.copyOf(array, array.length, String[].class);
        productList.setListData((String[]) names);
        nameTf.setText((String) names[Math.max(productList.getSelectedIndex(), 0)]);
      }
      case AbstractController.NAME -> nameTf.setText((String)item.value);
      case AbstractController.PRICE -> priceSpinner.setValue(item.value);
      case AbstractController.CODE -> codeSpinner.setValue(item.value);
      case AbstractController.QUANTITY -> quantitySpinner.setValue(item.value);
      case "FileWrite" -> fileStatusLbl.setText((String)item.value);
    }
  }

  @Override
  public void setController(AbstractController controller) {
    this.controller = (StockController) controller;
  }
}
