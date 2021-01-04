package com.Utils;

// Heap sort implementation
// Strategy pattern in use
// Pick a strategy to use to decide how the array will be sorted
// The strategy is the comparison to be used
// i.e. sortInteger will return first > second;
// sortIntegerReversed will return first < second;
// sortString will return string.charAt(i) > string.charAt(i);

public class Sort<T> {
  private ISort strategy;
  private final T[] array;

  public Sort(ISort strategy, T[] array) {
    this.strategy = strategy;
    this.array = array;
  }

  public Sort(T[] array) {
    this.array = array;
  }

  public void changeStrategy(ISort strategy) {
    this.strategy = strategy;
  }

  // Doesn't need to return anything as it directly changes the array
  public void doSort() throws Exception {
    if(strategy == null) {
      throw new Exception("No strategy set!");
    }
    buildMaxHeap();
    for(int endIndex = array.length - 1; endIndex > 0; endIndex--) {
      doSwap(0, endIndex);
      siftDown(0, endIndex - 1);

    }
  }

  private void buildMaxHeap() {
    int firstParentIndex = (array.length - 2) / 2;
    for(int currentIndex = firstParentIndex; currentIndex >= 0; currentIndex--) {
      siftDown(currentIndex, array.length - 1);
    }
  }

  private void siftDown(int currentIndex, int endIndex) {
    int childOneIndex = currentIndex * 2 + 1;
    while(childOneIndex <= endIndex) {
      int childTwoIndex = currentIndex * 2 + 2 <= endIndex ? currentIndex * 2 + 2 : -1;
      int indexToSwap;
      if(childTwoIndex != -1 && strategy.doCompare(this.array[childTwoIndex], this.array[childOneIndex])) {
        indexToSwap = childTwoIndex;
      } else {
        indexToSwap = childOneIndex;
      }
      if(strategy.doCompare(this.array[indexToSwap], this.array[currentIndex])) {
        doSwap(currentIndex, indexToSwap);
        currentIndex = indexToSwap;
        childOneIndex = currentIndex * 2 + 1;
      } else {
        return;
      }
    }
  }

  private void doSwap(int i, int j) {
    T temp = array[j];
    array[j] = array[i];
    array[i] = temp;
  }
}
