package com.controller;

import com.KeyValuePair;
import com.model.IModel;

import java.util.ArrayList;
import java.util.List;

public class MockItemModel implements IModel {

  private List<AbstractController> observers = new ArrayList<>();

  public MockItemModel() {

  }

  @Override
  public void subscribe(AbstractController observer) {
    this.observers.add(observer);
  }

  @Override
  public void unSubscribe(AbstractController observer) {

  }

  @Override
  public void onChange(KeyValuePair item) {

  }

  @Override
  public boolean writeToFile() {
    return false;
  }
}
