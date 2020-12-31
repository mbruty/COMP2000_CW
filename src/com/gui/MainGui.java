package com.gui;

import com.stock.Stock;
import com.stock.StockItem;

import javax.swing.*;
import java.awt.*;

public class MainGui extends JFrame {
    private JPanel ContentPanel;
    private JLabel loading;
    private JButton button1;
    private Stock stock;

    public MainGui(String title) {
        super(title);
        setContentPane(ContentPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        pack();
        button1.addActionListener(e -> {
            new SwingWorker<>() {

                @Override
                protected Object doInBackground() throws Exception {
                    loading.setText("Saving...");
                    stock.add(new StockItem("Bruty's Burgers", 2.99f, 11));
                    stock.writeToFile();
                    loading.setText("Saved");
                    return null;
                }
            }.execute();

        });
        new SwingWorker<>() {

            @Override
            protected Object doInBackground() throws Exception {
                stock = new Stock();
                loading.setText("Loaded");
                return null;
            }
        }.execute();
    }

    public static void main(String[] args) {
        MainGui main = new MainGui("Point of Sale");
        main.setVisible(true);
    }
}
