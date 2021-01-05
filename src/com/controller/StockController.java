package com.controller;

import com.KeyValuePair;
import com.Utils.Sort;
import com.Utils.SortItemsOnName;
import com.Utils.SortItemsOnPrice;
import com.Utils.SortItemsOnQuantity;
import com.model.IModel;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.model.ItemModel;
import com.view.AbstractView;

public class StockController extends AbstractController {

  private List<ItemModel> models;
  private final AbstractView view;

  IModel currentModel;

  private Method[] modelMethods;

  public StockController(List<ItemModel> models, AbstractView view) {
    this.models = models;
    this.view = view;

    currentModel = models.get(0);

    currentModel.subscribe(this);
    view.setController(this);

    modelMethods = currentModel.getClass().getDeclaredMethods();

    setupModel();
  }

  public void newModel() {
    //Create new empty model
    models.add(0, new ItemModel("New Item", 0.0f, 0, 0));
    // Update views with the new information
    updateView(new KeyValuePair<String>(NAME, "New Item"));
    // Update the view with the new NAMES list
    updateView(new KeyValuePair<String>(NAMES, null));
    // Update the view with the 0 values
    updateView(new KeyValuePair<Float>(PRICE, 0.0f));
    updateView(new KeyValuePair<Integer>(QUANTITY, 0));
    updateView(new KeyValuePair<Integer>(CODE, 0));
  }

  public void sortModels(String sortOn) {
    // Convert the list to array
    // List.toArray() returns an object that cannot be cast to IModel[]
    ItemModel[] array = new ItemModel[models.size()];
    for (int i = 0; i < models.size(); i++) {
      array[i] = models.get(i);
    }
    Sort<IModel> sort = new Sort<>(array);
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
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void updateView(KeyValuePair item) {
    if(item.key.equals(NAME) || item.key.equals(NAMES)) {
      List<String> names = new ArrayList<>();
      for (ItemModel model:
           models) {
        names.add(model.getName());
      }
      view.update(new KeyValuePair<String[]>(NAMES, names.toArray()));
      if(item.key.equals(NAME))
        view.update(new KeyValuePair<String>(NAME, item.value));
    } else {
      view.update(item);

    }
  }

  @Override
  public void setModelProperty(KeyValuePair data) {
    try {
      System.out.println(data.value);
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