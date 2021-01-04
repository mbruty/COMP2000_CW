package com;

import com.controller.AbstractController;
import com.controller.StockController;
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

  static String path = "resources/stock.csv";
  static String header;
  static List<ItemModel> items = new ArrayList<>();
  public static void main(String[] args) {

    readInFile();
    Admin adminForm = new Admin();
    PointOfSale pointOfSaleForm = new PointOfSale();
    StartupForm form = new StartupForm(adminForm, pointOfSaleForm);
    AbstractController adminController = new StockController(items, adminForm);
    form.setVisible(true);

    String[] options = new String[items.size()];
    for (int i = 0; i < items.size(); i++) {
      options[i] = items.get(i).getName();
    }

    adminForm.setListOptions(options);
  }

  static void readInFile(){
    try {
      File stockFile = new File(path);
      Scanner reader = new Scanner(stockFile);
      header = reader.nextLine() + "\n";
      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        items.add(new ItemModel(line));
      }
      reader.close();
    } catch (FileNotFoundException e) {
      // todo: log errors
      System.out.println(e.getMessage());
    }
  }
}
