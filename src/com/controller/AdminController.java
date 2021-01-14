package com.controller;

import com.KeyValuePair;
import com.model.AdminModel;
import com.view.AbstractView;
import com.view.Admin;

public class AdminController extends AbstractController {
  private final AdminModel model;
  private Admin view;

  public AdminController(AdminModel model) {
    this.model = model;
    this.model.subscribe(this);
  }

  public void setView(Admin view){
    this.view = view;
  }
  @Override
  public void updateView(KeyValuePair items) {
    this.view.update(items);
  }

  @Override
  public void setModelProperty(KeyValuePair data) {

  }

  // File writing is handled by the multi admin controller
  @Override
  public void writeFile() {

  }
}
