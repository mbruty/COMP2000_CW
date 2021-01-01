package com.view;

import com.KeyValuePair;
import com.controller.AbstractController;

import javax.swing.*;

public class PointOfSale extends AbstractView {
  private JPanel mainPanel;
  private JList list1;
  private JLabel cartLabel;
  private JTextField enterItemCodeTextField;
  private JButton addToCartButton;
  private JButton checkoutButton;
  private AbstractController controller;

  public PointOfSale() {
    this.setContentPane(mainPanel);
    this.initalise();
  }

  @Override
  public void update(KeyValuePair item) {

  }

  @Override
  public void setController(AbstractController controller) {
    this.controller = controller;
  }
}
