package com.controller;

import com.KeyValuePair;
import com.Main;
import com.model.AdminModel;
import com.model.IModel;
import com.view.AbstractView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MultiAdminController extends AbstractController {
  private IModel currentModel;
  private AbstractView view;
  private Method[] modelMethods;

  public static final String USER_NAME = "UserName";
  public static final String PASSWORD = "Password";
  public static final String USERS = "USERS";
  public static final String IS_ADMIN = "Admin";

  public MultiAdminController(List<IModel> models, AbstractView view) {
    this.models = models;
    this.view = view;
    this.view.setController(this);
    currentModel = models.get(0);
    modelMethods = currentModel.getClass().getDeclaredMethods();
    currentModel.subscribe(this);
  }

  public void delete(){
    this.models.remove(this.currentModel);
    currentModel = models.get(0);
    setupModel();
    // We want to write the deletion straight to file
    writeFile();
  }

  public void checkLogin(String userName, String password) {
    for (IModel model:
         models) {
      AdminModel current = (AdminModel) model;
      if(current.getUserName().equals(userName)) {
        if(current.validatePassword(password)){
          updateView(new KeyValuePair<AdminModel>("Validated", new AdminController(current)));
          if (!current.getAdmin()) updateView(new KeyValuePair<Boolean>("isAdmin", false));
          else {
            updateView(new KeyValuePair<MultiAdminController>("Validated admin", this));
          }
          return;
        } else {
          updateView(new KeyValuePair<String>("Validate", "Wrong Password"));
          return;
        }
      }
    }
    updateView(new KeyValuePair<String>("Validate", "No user found"));
  }

  public void updateNamesList() {
    List<String> userNames = new ArrayList<>();
    for (IModel model:
            models) {
      AdminModel current = (AdminModel) model;
      userNames.add(current.getUserName());
    }
    updateView(new KeyValuePair<List<String>>(USERS, userNames.toArray()));
  }

  public void newModel() {
    //Create new empty model
    models.add(0, new AdminModel());
    // Update views with the new information
    updateView(new KeyValuePair<String>(USER_NAME, "New Item"));
    // Reload view with new data
    setupModel();
    // Trigger the view to set the selected index to 0
    updateView(new KeyValuePair("NewItem", null));

  }

  public void setupModel() {
    // Get all names
    updateNamesList();
    // Get information for currently selected model
    try {
      for (Method method : modelMethods) {
        switch (method.getName()) {
          case "get" + USER_NAME -> {
            Object value = method.invoke(currentModel);
            updateView(new KeyValuePair<>(USER_NAME, value));
          }
          case "get" + IS_ADMIN -> {
            Object value = method.invoke(currentModel);
            updateView(new KeyValuePair<>(IS_ADMIN, value));
          }
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  public void setView(AbstractView view) { this.view = view; }
  @Override
  public void updateView(KeyValuePair items) {
    if(items.key.equals(USER_NAME)) {
      updateNamesList();
    }
    this.view.update(items);
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

  @Override
  public void writeFile() {
    updateView(new KeyValuePair<String>("AdminFileWrite", this.tryWriteFile()  ? "saved" : "failed"));
  }

  public boolean tryWriteFile() {
    return this.writeToFile(Main.ADMIN_PATH, Main.adminHeader);
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
}
