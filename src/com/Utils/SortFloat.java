package com.Utils;

public class SortFloat implements ISort{
  public SortFloat() { }
  @Override
  public boolean doCompare(Object itemOne, Object itemTwo) {
    return (Float)itemOne > (Float)itemTwo;
  }
}
