package com.view;

import com.controller.AbstractController;
import com.stock.StockItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class AbstractView extends JFrame {
    protected AbstractController controller;

    protected void initalise(int rows, int cols){
        this.setLayout(new GridLayout(rows, cols));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(600, 600));
        this.pack();

        this.setVisible(false);
    }

    public abstract void update(List<StockItem> items);
}
