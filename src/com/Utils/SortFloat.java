package com.Utils;

public class SortFloat implements ISort{
  public SortFloat() { }
  @Override
  // Simple first float bigger than second float
  public boolean doCompare(Object itemOne, Object itemTwo) {
    return (Float)itemOne > (Float)itemTwo;
  }
}
