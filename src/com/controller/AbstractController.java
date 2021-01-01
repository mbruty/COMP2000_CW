package com.controller;

import com.KeyValuePair;

public abstract class AbstractController {
  public static final String NAME = "Name";
  public static final String PRICE = "Price";
  public static final String QUANTITY = "Quantity";
  public static final String CODE = "Code";

  public abstract void updateView(KeyValuePair items);
  public abstract void setModelProperty(KeyValuePair data);

  public void swapModel(int index) { }
}
