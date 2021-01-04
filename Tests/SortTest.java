import com.Utils.Sort;
import com.Utils.SortInteger;
import com.Utils.SortString;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SortTest {

  @org.junit.jupiter.api.Test
  void changeStrategy() {
  }

  @org.junit.jupiter.api.Test
  void doSort() {
    // Class Integer over int for the type as primitive types cannot be given to generics
    final int nItems = 100000;
    Integer[] sortMe = new Integer[nItems];
    Random rand = new Random();
    for(int i = 0; i < nItems; i++) {
      sortMe[i] = rand.nextInt();
    }
    Sort<Integer> sort = new Sort<>(new SortInteger(), sortMe);
    sort.doSort();
    assertTrue(isSorted(sortMe));
  }

  @org.junit.jupiter.api.Test
  void doSortWithString(){
    String[] sortMe = new String[]{
      "aa", "X", "b", "x", "a", "A",
    };
    Sort<String> sort = new Sort<>(new SortString(), sortMe);
    sort.doSort();
    assertArrayEquals(new String[]{"A", "a", "aa", "b", "X", "x"}, sortMe);
  }
  boolean isSorted(Integer[] array) {
    for (int i = 0; i < array.length - 2; i++) {
      if(array[i] > array[i + 1]) {
        return false;
      }
    }
    return true;
  }
}