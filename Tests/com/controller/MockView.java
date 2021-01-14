package com.controller;

import com.KeyValuePair;
import com.view.AbstractView;

public class MockView extends AbstractView {

  public boolean writeDidFail = false;
  @Override
  public void update(KeyValuePair item) {
    if(item.key.equals("FileWrite") && item.value.equals("failed")) {
      writeDidFail = true;
    }
  }

  @Override
  public void setController(AbstractController controller) {

  }
}
