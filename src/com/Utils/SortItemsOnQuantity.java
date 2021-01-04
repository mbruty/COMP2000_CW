package com.Utils;

import com.model.ItemModel;

public class SortItemsOnQuantity implements ISort{
  private final boolean isAscending;
  public SortItemsOnQuantity(boolean isAscending) { this.isAscending = isAscending; }

  @Override
  public boolean doCompare(Object itemOne, Object itemTwo) {
    ItemModel firstItem = (ItemModel) itemOne;
    ItemModel secondItem = (ItemModel) itemTwo;
    boolean result = new SortInteger().doCompare(firstItem.getQuantity(), secondItem.getQuantity());
    return isAscending == result;
  }
}
