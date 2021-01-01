package com.model;

import com.controller.AbstractController;
import com.stock.StockItem;

import java.util.List;

public interface IModel {
    void subscribe(AbstractController observer);
    void unSubscribe(AbstractController observer);
    void onChange(List<StockItem> items);
}
