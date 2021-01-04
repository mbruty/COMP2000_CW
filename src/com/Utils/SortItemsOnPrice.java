package com.Utils;

import com.model.ItemModel;

public class SortItemsOnPrice implements ISort{
  private final boolean isAscending;
  public SortItemsOnPrice(boolean isAscending) { this.isAscending = isAscending; }

  @Override
  public boolean doCompare(Object itemOne, Object itemTwo) {
    ItemModel firstItem = (ItemModel) itemOne;
    ItemModel secondItem = (ItemModel) itemTwo;
    boolean result = new SortFloat().doCompare(firstItem.getPrice(), secondItem.getPrice());
    return isAscending == result;
  }
}
