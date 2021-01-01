package com.view;

import javax.swing.*;
import java.awt.*;

public class StartupForm extends JFrame{
    private JPanel mainPanel;
    private JButton adminButton;
    private JButton pointOfSalesButton;

    public StartupForm(AbstractView admin, AbstractView pointOfSale) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);
        adminButton.addActionListener(e -> {
            admin.setVisible(true);
            this.setVisible(false);
        });
        pointOfSalesButton.addActionListener(e -> {
            pointOfSale.setVisible(true);
            this.setVisible(false);
        });
    }
}
