package com.Utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortStringTest {

  @Test
  void doCompare() {
    ISort compare = new SortString();
    assertFalse(compare.doCompare("AAAAA", "AAAAAAAAAAAA"));
    assertFalse(compare.doCompare("A", "a"));
    assertFalse(compare.doCompare("a", "X"));
  }
}