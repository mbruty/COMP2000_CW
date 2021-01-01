package com.controller;

import com.KeyValuePair;
import com.model.IModel;
import java.lang.reflect.Method;
import java.util.List;

import com.model.ItemModel;
import com.view.AbstractView;

public class MultiModelController extends AbstractController {

  List<ItemModel> models;
  AbstractView view;

  IModel currentModel;

  private Method[] modelMethods;

  public MultiModelController(List<ItemModel> models, AbstractView view) {
    this.models = models;
    this.view = view;

    currentModel = models.get(0);

    currentModel.subscribe(this);
    view.setController(this);

    modelMethods = currentModel.getClass().getDeclaredMethods();

    setupModel();
  }

  @Override
  public void swapModel(int index) {

    if(index >= 0 && index < models.size()) {
      if (currentModel != models.get(index)) {
        currentModel.unSubscribe(this);

        currentModel = models.get(index);
        currentModel.subscribe(this);

        modelMethods = currentModel.getClass().getDeclaredMethods();
      }
    }

    setupModel();
  }

  private void setupModel() {
    try {
      for(Method method : modelMethods) {
        switch (method.getName()) {
          case "get" + NAME -> {
            Object value = method.invoke(currentModel);
            updateView(new KeyValuePair<>(NAME, (String) value));
          }
          case "get" + PRICE -> {
            Object value = method.invoke(currentModel);
            updateView(new KeyValuePair<>(PRICE, (float) value));
          }
          case "get" + QUANTITY -> {
            Object value = method.invoke(currentModel);
            updateView(new KeyValuePair<>(QUANTITY, (int) value));
          }
          case "get" + CODE -> {
            Object value = method.invoke(currentModel);
            updateView(new KeyValuePair<>(CODE, (int) value));
          }
        }
      }
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void updateView(KeyValuePair item) {
    view.update(item);
  }

  @Override
  public void setModelProperty(KeyValuePair data) {
    try {
      String methodName = "set" + data.key;
      for(Method method : modelMethods) {
        if (method.getName().equals(methodName)) {
          method.invoke(currentModel, data.value);
        }
      }
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
}