package com;

import com.controller.AbstractController;
import com.controller.MultiAdminController;
import com.controller.StockController;
import com.model.AdminModel;
import com.model.IModel;
import com.model.ItemModel;
import com.view.Admin;
import com.view.PointOfSale;
import com.view.StartupForm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

  public final static String STOCK_PATH = "resources/stock.csv";
  public final static String ADMIN_PATH = "resources/users.csv";
  public static String header;
  public static String adminHeader;
  static List<IModel> items = new ArrayList<>();
  static List<IModel> users = new ArrayList<>();
  public static void main(String[] args) {

    readInStockFile();
    readInUserFile();
    Admin adminForm = new Admin();
    PointOfSale pointOfSaleForm = new PointOfSale();
    StartupForm form = new StartupForm(adminForm, pointOfSaleForm);
    new StockController(items, adminForm);
    new StockController(items, pointOfSaleForm);
    form.setController(new MultiAdminController(users, form));
    form.setVisible(true);

    String[] options = new String[items.size()];
    for (int i = 0; i < items.size(); i++) {
      ItemModel current = (ItemModel) items.get(i);
      options[i] = current.getName();
    }

    adminForm.setListOptions(options);
  }

  static void readInStockFile(){
    try {
      File stockFile = new File(STOCK_PATH);
      Scanner reader = new Scanner(stockFile);
      header = reader.nextLine() + "\n";
      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        items.add(new ItemModel(line));
      }
      reader.close();
    } catch (FileNotFoundException e) {
      // todo: log errors
      e.printStackTrace();
    }
  }

  static void readInUserFile() {
    try{
      File userFile = new File(ADMIN_PATH);
      Scanner reader = new Scanner(userFile);
      adminHeader = reader.nextLine() + '\n';
      while(reader.hasNextLine()) {
        String line = reader.nextLine();
        users.add(new AdminModel(line));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
