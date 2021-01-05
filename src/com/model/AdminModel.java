package com.model;

import com.KeyValuePair;
import com.controller.AbstractController;

import java.util.ArrayList;
import java.util.List;

public class AdminModel implements IModel{
  private List<AbstractController> observers = new ArrayList<>();

  private String userName;
  private String password;

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
    for (AbstractController observer:
         observers) {
      observer.updateView(item);
    }
  }
}
