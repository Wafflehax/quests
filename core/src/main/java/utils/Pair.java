package utils;

import java.util.AbstractMap;

public class Pair<T1, T2> extends AbstractMap.SimpleEntry<T1, T2> {

  private T1 t1;
  private T2 t2;

  public Pair(T1 t1, T2 t2) {
    super(t1, t2);
  }
}
