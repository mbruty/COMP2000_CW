package com.Utils;

import com.model.ItemModel;

public class SortItemsOnName implements ISort{
  private final boolean isAscending;
  public SortItemsOnName(boolean isAscending) { this.isAscending = isAscending; }

  @Override
  public boolean doCompare(Object itemOne, Object itemTwo) {
    ItemModel firstItem = (ItemModel) itemOne;
    ItemModel secondItem = (ItemModel) itemTwo;
    boolean result = new SortString().doCompare(firstItem.getName(), secondItem.getName());
    return isAscending == result;
  }
}
