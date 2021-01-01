package com.controller;

import com.stock.StockItem;

import java.util.List;

public abstract class AbstractController {
    public abstract void updateView(List<StockItem> items);
}
