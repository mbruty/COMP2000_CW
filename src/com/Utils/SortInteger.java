package com.Utils;

public class SortInteger implements ISort{
  public SortInteger() { }
  @Override
  public boolean doCompare(Object itemOne, Object itemTwo) {
    return (Integer)itemOne > (Integer)itemTwo;
  }
}
