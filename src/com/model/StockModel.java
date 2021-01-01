package com.model;

import com.controller.AbstractController;
import com.stock.StockItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StockModel implements IModel{

    private final List<AbstractController> observers = new ArrayList<>();
    private final List<StockItem> stock = new ArrayList<>();
    @Override
    public void subscribe(AbstractController observer) {
        observers.add(observer);
    }

    @Override
    public void unSubscribe(AbstractController observer) {
        observers.remove(observer);
    }

    @Override
    public void onChange(List<StockItem> item) {
        for (AbstractController controller:
             observers) {
            controller.updateView(stock);
        }
    }

    private final List<StockItem> items = new ArrayList<>();
    private final String path = "resources/stock.csv";
    private String header;

    public StockModel() {
        try {
            File stockFile = new File(path);
            Scanner reader = new Scanner(stockFile);
            header = reader.nextLine() + "\n";
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                items.add(new StockItem(line));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            // todo: log errors
            System.out.println(e.getMessage());
        }
    }

    public void writeToFile() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path);
             FileChannel channel = fileOutputStream.getChannel();
             FileLock lock = channel.lock()) {
            Thread.sleep(5000);
            fileOutputStream.write(header.getBytes(StandardCharsets.UTF_8));
            for (StockItem item:
                    items) {
                fileOutputStream.write(
                        item.toCommaDelimited()
                                .getBytes(StandardCharsets.UTF_8)
                );
            }
            // write to the channel
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
