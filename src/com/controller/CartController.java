package com.controller;

import com.KeyValuePair;
import com.model.IModel;
import com.model.ItemModel;
import com.receipt.ReceiptSingleton;
import com.view.AbstractView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CartController extends AbstractController {
  private final AbstractView view;
  public static final String CART_LIST = "cart_list";
  public static final String TOTAL = "Total";
  public CartController(AbstractView view) {
    this.view = view;
    this.models = new ArrayList<>();
  }

  public void addModel(IModel modelToAdd) {
    ItemModel stockModel = (ItemModel) modelToAdd;
    for (IModel model:
         models) {
      ItemModel current = (ItemModel) model;
      if(current.getCode() == stockModel.getCode()) {
        setModelProperty(new KeyValuePair<Integer>("Quantity", current.getQuantity() + 1), model);
        this.updateView(new KeyValuePair<>(null, null));
        return;
      }
    }
    ItemModel cloned = stockModel.clone();
    cloned.subscribe(this);
    this.models.add(cloned);
    this.updateView(new KeyValuePair<>(null, null));
  }

  public void reset() {
    this.models = new ArrayList<>();
  }

  public void craftRecipt() {
    float total = 0;
    List<ItemModel> itemModels = new ArrayList<>();
    for (IModel model:
            models) {
      ItemModel current = (ItemModel) model;
      total += current.getPrice() * current.getQuantity();
      itemModels.add(current);
    }
    ReceiptSingleton.getInstance().getBuilder().setPrice(total);
    ReceiptSingleton.getInstance().getBuilder().setItemsBought(itemModels);
  }

  @Override
  public void updateView(KeyValuePair items) {
    List<String> cartList = new ArrayList<>();
    float total = 0;
    for (IModel model:
         models) {
      ItemModel current = (ItemModel) model;
      cartList.add(current.getQuantity() + "x: " + current.getName());
      total += current.getPrice() * current.getQuantity();
    }
    this.view.update(new KeyValuePair<String>(CART_LIST, cartList.toArray()));
    this.view.update(new KeyValuePair<Float>(TOTAL, total));
  }

  @Override
  public void setModelProperty(KeyValuePair data) {

  }

  public void setModelProperty(KeyValuePair data, IModel model) {
    try {
      Method[] modelMethods = model.getClass().getDeclaredMethods();
      String methodName = "set" + data.key;
      for (Method method : modelMethods) {
        if (method.getName().equals(methodName)) {
          method.invoke(model, data.value);
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  @Override
  public void writeFile() {

  }
}
