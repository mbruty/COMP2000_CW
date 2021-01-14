package com;

public class KeyValuePair<T> {
  public String key;
  public T value;
  public KeyValuePair(String key, Object value) {
    this.key = key;
    this.value = (T) value;
  }
}
