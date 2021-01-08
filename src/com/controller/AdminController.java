package com.controller;

import com.KeyValuePair;
import com.model.AdminModel;
import com.view.AbstractView;
import com.view.Admin;

public class AdminController extends AbstractController {
  private AdminModel model;
  private Admin view;

  public AdminController(AdminModel model, Admin view) {
    this.model = model;
    this.view = view;
    this.model.subscribe(this);
    view.setAdminController(this);
  }

  @Override
  public void updateView(KeyValuePair items) {
    this.view.update(items);
  }

  @Override
  public void setModelProperty(KeyValuePair data) {

  }
}
