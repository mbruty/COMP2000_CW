package com.controller;

import com.KeyValuePair;
import com.Main;
import com.model.IModel;
import com.model.ItemModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public abstract class AbstractController {

  public abstract void updateView(KeyValuePair items);
  public abstract void setModelProperty(KeyValuePair data);
  public abstract void writeFile();
  protected List<IModel> models;
  // Boolean as we cannot access the view here to update
  // We'll do the check to see if write is successful
  protected boolean writeHeader(String path, String header) {
    try {
      FileWriter writer = new FileWriter(path + ".tmp");
      // Get the header that was saved in main
      writer.write(header);
      writer.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  protected boolean writeToFile(String path, String header) {
    // Write the header to the file
    if(!writeHeader(path, header)){
      return false;
    }
    for (IModel model:
            models) {
      if(!model.writeToFile()){
        return false;
      }
    }
    // If the write was successful, commit
    File file = new File(path + ".tmp");
    File rename = new File(path);
    return file.renameTo(rename);
  }

  public void swapModel(int index) { }
}
