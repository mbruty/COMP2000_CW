package com.payment;

import java.util.ArrayList;
import java.util.List;

public class Cash{
  // Denominations raging from 1p to £50
  public static final int[] denoms = new int[] {5000, 2000, 1000, 500, 200, 100, 50, 20, 5, 2, 1};

  public static List<Integer> calcChange(float total, float amountPaid) throws Exception {
    if(total > amountPaid) {
      throw new Exception("Total cannot be larger than the amount paid");
    }
    List<Integer> result = new ArrayList<>();
    // Convert the total to pence
    // We can just truncate it as we only care about the 2 decimals and they aren't there any more after * 100
    int changeInPennies =  Math.round((amountPaid - total) * 100);
    for (int denom:
            denoms) {
      // Remove denom amount until it cannot be removed any more
      while((changeInPennies - denom) >= 0) {
        changeInPennies -= denom;
        result.add(denom);
      }
    }
    return result;
  }
}
