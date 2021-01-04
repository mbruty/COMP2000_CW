package com.Utils;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

// From https://stackoverflow.com/questions/16427319/cast-object-to-array
public class ObjectToArray {
  public static Object[] convertToObjectArray(Object array) {
    if (array.getClass().getComponentType().isPrimitive()) {
      List<Object> ar = new ArrayList<>();
      int length = Array.getLength(array);
      for (int i = 0; i < length; i++) {
        ar.add(Array.get(array, i));
      }
      return ar.toArray();
    }
    else {
      return (Object[]) array;
    }
  }
}
