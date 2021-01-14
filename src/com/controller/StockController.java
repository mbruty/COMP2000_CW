package com.controller;

import com.KeyValuePair;
import com.Main;
import com.Utils.Sort;
import com.Utils.SortItemsOnName;
import com.Utils.SortItemsOnPrice;
import com.Utils.SortItemsOnQuantity;
import com.model.IModel;
import com.model.ItemModel;
import com.view.AbstractView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StockController extends AbstractController {

  public static final String NAME = "Name";
  public static final String PRICE = "Price";
  public static final String QUANTITY = "Quantity";
  public static final String CODE = "Code";
  public static final String ORDER_QUANTITY = "OrderQuantity";
  public static final String NAMES = "";
  public static final String LOW_QUANTITY = "lowQuantity";
  private final AbstractView view;

  private IModel currentModel;

  private Method[] modelMethods;

  public StockController(List<IModel> models, AbstractView view) {
    this.models = models;
    this.view = view;

    currentModel = models.get(0);

    currentModel.subscribe(this);
    view.setController(this);

    modelMethods = currentModel.getClass().getDeclaredMethods();

    setupModel();
  }

  public ItemModel getItemByCode(int code) throws Exception {
    // Loop through all the items
    for (IModel model:
         models) {
      ItemModel itemModel = (ItemModel) model;
      // If the code matches
      if(itemModel.getCode() == code )
        // We're done! Return the model associated with the code
        return itemModel;
    }
    // No model found, throw a not found exception
    throw new Exception("Not found");
  }

  public void newModel() {
    //Create new empty model
    models.add(0, new ItemModel("New Item", 0.0f, 0, 0, 0));
    // Update views with the new information
    updateView(new KeyValuePair<String>(NAME, "New Item"));
    // Update the view with the new NAMES list
    updateView(new KeyValuePair<String>(NAMES, null));
    updateView(new KeyValuePair("NewItem", null));
    setupModel();
  }
  
  public void updateStock(List<IModel> cart) {
    for (IModel model:
         cart) {
      ItemModel item = (ItemModel) model;
      try {
        ItemModel stockItem = getItemByCode(item.getCode());
        stockItem.setQuantity(
                stockItem.getQuantity() - item.getQuantity()
        );
      } catch (Exception e) {
        // As the items are duplicated from the stock model, this should never error
        e.printStackTrace();
        return;
      }
    }
    this.writeToFile(Main.STOCK_PATH, Main.header);

  }

  public void sortModels(String sortOn) {
    // Convert the list to array
    // List.toArray() returns an object that cannot be cast to IModel[]
    ItemModel[] array = new ItemModel[models.size()];
    for (int i = 0; i < models.size(); i++) {
      array[i] = (ItemModel) models.get(i);
    }
    Sort<IModel> sort = new Sort<>(array);
    // Set the strategy from what type of sort is selected
    switch(sortOn) {
      case "Name ASC" -> {
        sort.changeStrategy(new SortItemsOnName(true));
      }
      case "Name DSC" -> {
        sort.changeStrategy(new SortItemsOnName(false));
      }
      case "Price ASC" -> {
        sort.changeStrategy(new SortItemsOnPrice(true));
      }
      case "Price DSC" -> {
        sort.changeStrategy(new SortItemsOnPrice(false));
      }
      case "Quantity ASC" -> {
        sort.changeStrategy(new SortItemsOnQuantity(true));
      }
      case "Quantity DSC" -> {
        sort.changeStrategy(new SortItemsOnQuantity(false));
      }
    }
    try{
      sort.doSort();
    } catch (Exception e) {
      e.printStackTrace();
    }

    models = Arrays.asList(array);

    // Update view gets all the names from the model when called with NAMES
    // So it's fine to call with null ONLY for NAMES
    this.updateView(new KeyValuePair<String[]>(NAMES, null));
  }

  @Override
  public void swapModel(int index) {

    if (index >= 0 && index < models.size()) {
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
      for (Method method : modelMethods) {
        switch (method.getName()) {
          case "get" + NAME -> {
            Object value = method.invoke(currentModel);
            updateView(new KeyValuePair<>(NAME, value));
          }
          case "get" + PRICE -> {
            Object value = method.invoke(currentModel);
            updateView(new KeyValuePair<>(PRICE, value));
          }
          case "get" + QUANTITY -> {
            Object value = method.invoke(currentModel);
            updateView(new KeyValuePair<>(QUANTITY, value));
          }
          case "get" + CODE -> {
            Object value = method.invoke(currentModel);
            updateView(new KeyValuePair<>(CODE, value));
          }
          case "get" + ORDER_QUANTITY -> {
            Object value = method.invoke(currentModel);
            updateView(new KeyValuePair<Integer>(ORDER_QUANTITY, value));
          }
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void writeFile() {
    updateView(new KeyValuePair<String>("FileWrite", this.writeToFile(Main.STOCK_PATH, Main.header) ? "saved" : "failed"));
  }

  @Override
  public void updateView(KeyValuePair item) {
    if(item.key.equals(NAME) || item.key.equals(NAMES)) {
      List<String> names = new ArrayList<>();
      for (IModel model:
           models) {
        ItemModel current = (ItemModel) model;
        names.add(current.getName());
      }
      view.update(new KeyValuePair<String[]>(NAMES, names.toArray()));
      if(item.key.equals(NAME))
        view.update(new KeyValuePair<String>(NAME, item.value));
    } else if(item.key.equals(QUANTITY)) {
      List<String> lowQuantity = new ArrayList<>();
      for (IModel model:
              models) {
        ItemModel current = (ItemModel) model;
        if(current.getQuantity() < 10) {
          lowQuantity.add(current.getName());
        }
      }
      view.update(new KeyValuePair<String[]>(LOW_QUANTITY, lowQuantity.toArray()));
      view.update(item);
    } else {
      view.update(item);

    }
  }

  @Override
  public void setModelProperty(KeyValuePair data) {
    try {
      String methodName = "set" + data.key;
      for (Method method : modelMethods) {
        if (method.getName().equals(methodName)) {
          method.invoke(currentModel, data.value);
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}