package com.view;

import com.stock.StockItem;

import javax.swing.*;
import java.util.List;

public class Admin extends AbstractView {
    private JPanel mainPanel;

    public Admin() {
        this.setContentPane(mainPanel);
        this.initalise(1, 1);
    }

    @Override
    public void update(List<StockItem> items) {

    }
}
