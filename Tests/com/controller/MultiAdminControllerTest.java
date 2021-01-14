package com.controller;

import com.model.IModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultiAdminControllerTest {

  private StockController controller;
  private MockView view;

  @BeforeEach
  public void setup() {
    List<IModel> testList = new ArrayList<>();
    view = new MockView();
    testList.add(new MockItemModel());
    this.controller = new StockController(testList, view);
  }

  @Test
  void writeFile() {
    controller.writeFile("Test");
    assertTrue(view.writeDidFail);
  }
}