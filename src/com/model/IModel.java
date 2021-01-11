package com.model;

import com.KeyValuePair;
import com.controller.AbstractController;

public interface IModel {
  void subscribe(AbstractController observer);
  void unSubscribe(AbstractController observer);
  void onChange(KeyValuePair item);
  boolean writeToFile();
}
