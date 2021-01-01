package com.model;

import com.KeyValuePair;
import com.controller.AbstractController;

import java.util.ArrayList;
import java.util.List;

public class ItemModel implements IModel{

  private final List<AbstractController> observers = new ArrayList<>();
  private String name;
  private float price;
  private int quantity;
  private int code;

  public ItemModel (String commaDelimitedLine) {
    String[] items = commaDelimitedLine.split(",");
    name = items[0];
    try {
      quantity = Integer.parseInt(items[1]);
      code = Integer.parseInt(items[2]);
      price = Float.parseFloat(items[3]);
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void subscribe(AbstractController observer) {
    observers.add(observer);
  }

  @Override
  public void unSubscribe(AbstractController observer) {
    observers.remove(observer);
  }

  @Override
  public void onChange(KeyValuePair item) {
    for (AbstractController controller:
            observers) {
      controller.updateView(item);
    }
  }

  public String getName() {
    return name;
  }

  public float getPrice() {
    return price;
  }

  public int getQuantity() {
    return quantity;
  }

  public int getCode(){
    return code;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public void setCode(int code) {
    this.code = code;
  }
}
