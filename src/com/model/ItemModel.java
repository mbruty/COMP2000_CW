package com.model;

import com.KeyValuePair;
import com.Main;
import com.controller.AbstractController;
import com.controller.StockController;

import java.io.FileWriter;
import java.io.IOException;
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

  public ItemModel(String name, float price, int quantity, int code) {
    this.name = name;
    this.price = price;
    this.quantity = quantity;
    this.code = code;
  }

  public ItemModel () {
    
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

  public ItemModel clone() {
    ItemModel newModel = new ItemModel();
    newModel.setName(this.name);
    newModel.setCode(this.code);
    newModel.setPrice(this.price);
    // We're cloning in CartController, so this will be set to 1
    newModel.setQuantity(1);
    return newModel;
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
    onChange(new KeyValuePair<String>(StockController.NAME, name));
  }

  public void setPrice(double price) {
    this.price = (float) price;
    onChange(new KeyValuePair<Float>(StockController.PRICE, price));
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
    onChange(new KeyValuePair<Integer>(StockController.QUANTITY, quantity));
  }

  public void setCode(int code) {
    this.code = code;
    onChange(new KeyValuePair<Integer>(StockController.CODE, code));
  }

  public boolean writeToFile() {
    try {
      // Write in append mode
      FileWriter writer = new FileWriter(Main.STOCK_PATH + ".tmp", true);
      writer.write(this.name + ',' + this.quantity + ',' + this.code + ',' + this.price + '\n');
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
