package com.controller;

import com.KeyValuePair;
import com.model.AdminModel;
import com.view.AbstractView;
import com.view.Admin;

import java.util.List;

public class MultiAdminController extends AbstractController {
  private List<AdminModel> models;
  private AbstractView view;

  public MultiAdminController(List<AdminModel> models, AbstractView view) {
    this.models = models;
    this.view = view;
    for (AdminModel model :
      models) {
      model.subscribe(this);
    }
    this.view.setController(this);
  }
  public void checkLogin(String userName, String password) {
    for (AdminModel model:
         models) {
      if(model.getUserName().equals(userName)) {
        if(model.validatePassword(password)){
          updateView(new KeyValuePair<String>("Validate", "Validated"));
          return;
        } else {
          updateView(new KeyValuePair<String>("Validate", "Wrong Password"));
          return;
        }
      }
    }
    updateView(new KeyValuePair<String>("Validate", "No user found"));
  }
  @Override
  public void updateView(KeyValuePair items) {
    this.view.update(items);
  }

  @Override
  public void setModelProperty(KeyValuePair data) {

  }
}
