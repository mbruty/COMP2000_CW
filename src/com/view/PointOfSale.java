package com.view;

import com.KeyValuePair;
import com.Utils.ObjectToArray;
import com.controller.AbstractController;
import com.controller.CartController;
import com.controller.StockController;

import javax.swing.*;
import java.util.Arrays;

public class PointOfSale extends AbstractView {
  private JPanel mainPanel;
  private JList<String> list1;
  private JLabel cartLabel;
  private JTextField enterItemCodeTextField;
  private JButton addToCartButton;
  private JButton checkoutButton;
  private JLabel errorTxtLbl;
  private JLabel totalLbl;
  private StockController stockController;
  private CartController cartController;
  public PointOfSale() {
    this.setContentPane(mainPanel);
    this.addToCartButton.addActionListener(
            e -> {
              try {
                cartController.addModel(
                        stockController.getItemByCode(Integer.parseInt(enterItemCodeTextField.getText()))
                );
                errorTxtLbl.setText("");
              } catch (Exception ex) {
                ex.printStackTrace();
                errorTxtLbl.setText("Error finding item");
              }
            }
    );
    this.cartController = new CartController(this);
    this.initalise();
  }

  @Override
  public void update(KeyValuePair item) {
    switch (item.key) {
      case CartController.CART_LIST -> {
        Object[] array =  ObjectToArray.convertToObjectArray(item.value);
        String[] cart = Arrays.copyOf(array, array.length, String[].class);
        list1.setListData(cart);
      }
      case CartController.TOTAL -> totalLbl.setText("Total: " + item.value);
    }
  }

  @Override
  public void setController(AbstractController controller) {
    this.stockController = (StockController) controller;
  }

  public void setCartController(AbstractController controller) {

  }
}
