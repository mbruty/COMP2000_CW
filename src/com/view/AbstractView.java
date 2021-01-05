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
    this.center();
    this.setVisible(false);
  }

  protected void center(){
    Dimension windowSize = getSize();
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Point centerPoint = ge.getCenterPoint();

    int dx = centerPoint.x - windowSize.width / 2;
    int dy = centerPoint.y - windowSize.height / 2;
    setLocation(dx, dy);
  }

  public abstract void update(KeyValuePair item);

  public abstract void setController(AbstractController controller);
}
