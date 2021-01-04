package com.Utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortIntegerTest {

  @Test
  void doCompare() {
    ISort compare = new SortInteger();
    assertTrue(compare.doCompare(2, 1));
  }
}