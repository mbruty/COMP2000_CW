package com;

import com.model.IModel;
import com.model.StockModel;
import com.view.Admin;
import com.view.PointOfSale;
import com.view.StartupForm;

public class Main {
    public static void main(String[] args) {
        IModel stock = new StockModel();
        Admin adminForm = new Admin();
        PointOfSale pointOfSaleForm = new PointOfSale();
        StartupForm form = new StartupForm(adminForm, pointOfSaleForm);
    }
}
