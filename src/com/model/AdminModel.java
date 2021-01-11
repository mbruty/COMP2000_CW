package com.model;

import com.KeyValuePair;
import com.Main;
import com.controller.AbstractController;
import com.controller.AdminController;
import com.controller.MultiAdminController;
import org.mindrot.jbcrypt.BCrypt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminModel implements IModel{
  private final List<AbstractController> observers = new ArrayList<>();

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

  @Override
  public boolean writeToFile() {
    try {
      // Write in append mode
      FileWriter writer = new FileWriter(Main.ADMIN_PATH + ".tmp", true);
      writer.write(this.userName + ',' + this.password + ',' + String.valueOf(this.isAdmin) + '\n');
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
    onChange(new KeyValuePair<String>(MultiAdminController.USER_NAME,this.userName));
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
    onChange(new KeyValuePair<String>(MultiAdminController.PASSWORD, this.password));
  }

  public boolean validatePassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }
  public boolean getAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }
}
