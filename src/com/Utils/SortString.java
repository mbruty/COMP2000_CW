package com.Utils;

public class SortString implements ISort{
  public SortString() { }
  @Override
  public boolean doCompare(Object itemOne, Object itemTwo) {
    // Cast each to strings
    String stringOne = (String) itemOne;
    String stringTwo = (String) itemTwo;
    for(int i = 0; i < Math.min(stringOne.length(), stringTwo.length()); i++) {
      // If they are not the same char
      if(stringOne.toLowerCase().charAt(i) != stringTwo.toLowerCase().charAt(i)) {
        // Has to check lowercase as X is smaller than a
        return stringOne.toLowerCase().charAt(i) > stringTwo.toLowerCase().charAt(i);
      }
      // They are the same char, BUT one is uppercase and one is lowercase
      else if (stringOne.charAt(i) != stringTwo.charAt(i)) {
        return stringOne.charAt(i) > stringTwo.charAt(i);
      }
    }
    // They are the same string up until the length of the shortest one!
    // Now we will sort on the longest one.
    // If they are exactly the same string, the object may or may not get swapped,
    // but it won't have any affect to the final output
    return stringOne.length() > stringTwo.length();
  }
}
