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
    // Loop through the cart
    for (IModel model:
         models) {
      ItemModel current = (ItemModel) model;
      // If the cart contains the item code, increment it's quantity
      if(current.getCode() == stockModel.getCode()) {
        setModelProperty(new KeyValuePair<Integer>("Quantity", current.getQuantity() + 1), model);
        this.updateView(new KeyValuePair<>(null, null));
        // We're done if we hit the if statement, exit
        return;
      }
    }
    // If the item wasn't found, clone it and add it to the cart
    ItemModel cloned = stockModel.clone();
    cloned.subscribe(this);
    this.models.add(cloned);
    this.updateView(new KeyValuePair<>(null, null));
  }

  public List<IModel> getCart() { return this.models; }

  // Reset the cart to an empty list
  // This is done after a successful transaction
  public void reset() {
    this.models = new ArrayList<>();
  }

  public void craftRecipt() {
    float total = 0;
    List<ItemModel> itemModels = new ArrayList<>();
    // Loop through the models
    for (IModel model:
            models) {
      ItemModel current = (ItemModel) model;
      // Add the item price multiplied by the quantity to the total
      total += current.getPrice() * current.getQuantity();
      // Add the casted item to the list
      itemModels.add(current);
    }

    // Set the price and items bought of the receipt
    ReceiptSingleton.getInstance().getBuilder()
            .setPrice(total)
            .setItemsBought(itemModels);
  }

  @Override
  public void updateView(KeyValuePair items) {
    List<String> cartList = new ArrayList<>();
    float total = 0;
    // Loop through the cart and recalculate the total and add the name to the string array
    // The string array is displayed as a list in the cart
    for (IModel model:
         models) {
      ItemModel current = (ItemModel) model;
      cartList.add(current.getQuantity() + "x: " + current.getName());
      total += current.getPrice() * current.getQuantity();
    }
    // Update the view with the new list
    this.view.update(new KeyValuePair<String>(CART_LIST, cartList.toArray()));
    // Update the view with the new total
    this.view.update(new KeyValuePair<Float>(TOTAL, total));
  }

  // We're not setting anything there
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
