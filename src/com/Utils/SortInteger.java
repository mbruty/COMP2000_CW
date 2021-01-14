package com.Utils;

public class SortInteger implements ISort{
  public SortInteger() { }
  @Override
  // Simple first int is larger than second int
  public boolean doCompare(Object itemOne, Object itemTwo) {
    return (Integer)itemOne > (Integer)itemTwo;
  }
}
