package com.model;

import com.KeyValuePair;
import com.controller.AbstractController;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class AdminModel implements IModel{
  private List<AbstractController> observers = new ArrayList<>();

  private String userName;
  private String password;
  private boolean isAdmin;

  public AdminModel(String commaDelimitedLine) {
    String[] items = commaDelimitedLine.split(",");
    this.userName = items[0];
    this.password = items[1];
    this.isAdmin = Boolean.parseBoolean(items[2]);
  }

  public AdminModel() { }

  @Override
  public void subscribe(AbstractController observer) {
    observers.add(observer);
  }

  @Override
  public void unSubscribe(AbstractController observer) {
    observers.remove(observer);
  }

  @Override
  public void onChange(KeyValuePair item) {
    for (AbstractController observer:
         observers) {
      observer.updateView(item);
    }
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void hashAndSetPassword(String password)  {
    this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
  }

  public boolean validatePassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }
  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }
}
