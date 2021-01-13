package com.payment;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CashTest {

  @Test
  void calcChangeThrowsError()  {

    try{
      Cash.calcChange(50f, 10f);
    } catch (Exception e) {
      assertEquals("Total cannot be larger than the amount paid", e.getMessage());
    }
  }

  @Test
  void calcChangeSimple() {
    try {
      List<Integer> result = Cash.calcChange(9.5f, 10);
      List<Integer> expected = new ArrayList<>();
      expected.add(50);
      assertArrayEquals(expected.toArray(), result.toArray());
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void calcChangeComplicated() {
    try {
      List<Integer> result = Cash.calcChange(5921.52f, 6000);
      List<Integer> expected = new ArrayList<>();
      expected.add(5000);
      expected.add(2000);
      expected.add(500);
      expected.add(200);
      expected.add(100);
      expected.add(20);
      expected.add(20);
      expected.add(5);
      expected.add(2);
      expected.add(1);
      assertArrayEquals(expected.toArray(), result.toArray());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}