package com.view;

import com.KeyValuePair;
import com.Utils.ObjectToArray;
import com.controller.AbstractController;
import com.controller.CartController;
import com.controller.StockController;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class PointOfSale extends AbstractView {
  private JPanel mainPanel;
  private JList<String> list1;
  private JLabel cartLabel;
  private JTextField enterItemCodeTextField;
  private JButton addToCartButton;
  private JButton payWithCardButton;
  private JLabel errorTxtLbl;
  private JLabel totalLbl;
  private JButton payWithCashButton;
  private StockController stockController;
  private CartController cartController;
  private float currentTotal = 0f;
  public PointOfSale() {
    this.setContentPane(mainPanel);
    payWithCardButton.addActionListener(
            e -> new CardPayment(currentTotal).addActionListener(this)
    );
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
    this.setPreferredSize(new Dimension(500, 600));
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
      case CartController.TOTAL -> {
        totalLbl.setText("Total: " + item.value);
        this.currentTotal = (float) item.value;
      }
    }
  }

  @Override
  public void setController(AbstractController controller) {
    this.stockController = (StockController) controller;
  }

  public void setCartController(AbstractController controller) {

  }
}
