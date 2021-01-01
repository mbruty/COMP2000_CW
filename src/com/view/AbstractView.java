package com.view;

import com.KeyValuePair;
import com.controller.AbstractController;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractView extends JFrame {
  protected AbstractController controller;

  protected void initalise(){
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(600, 600));
    this.pack();
    this.setVisible(false);
  }

  public abstract void update(KeyValuePair item);

  public abstract void setController(AbstractController controller);
}
