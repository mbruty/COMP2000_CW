package com.Utils;

import java.util.List;

// Simple function to convert pennies to their corresponding coin / note
public class ChangeToString {
  public static String convert(List<Integer> pennies) {
    StringBuilder result = new StringBuilder();
    for (int denom:
         pennies) {
      switch (denom) {
        case 1 -> result.append("1p coin").append("\n");
        case 2 -> result.append("2p coin").append("\n");
        case 5 -> result.append("5p coin").append("\n");
        case 10 -> result.append("10p coin").append("\n");
        case 20 -> result.append("20p coin").append("\n");
        case 50 -> result.append("50p coin").append("\n");
        case 100 -> result.append("£1 coin").append("\n");
        case 200 -> result.append("£2 coin").append("\n");
        case 500 -> result.append("£5 note").append("\n");
        case 1000 -> result.append("£10 note").append("\n");
        case 2000 -> result.append("£20 note").append("\n");
        case 5000 -> result.append("£50 note").append("\n");
        default -> result.append(denom).append("not implemented").append("\n");
      }
    }
    return result.toString();
  }
}
