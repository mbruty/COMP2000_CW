package com.stock;

public class StockItem {
    private final String name;
    private float price;
    private int quantity;

    public StockItem(String commaDelimitedLine) {
        String[] items = commaDelimitedLine.split(",");
        name = items[0];
        try{
            quantity = Integer.parseInt(items[1]);
            price = Float.parseFloat(items[2]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public StockItem(String name, float price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String toCommaDelimited() {
        return this.name + ',' + quantity + ',' + price + '\n';
    }
}
