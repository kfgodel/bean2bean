package info.kfgodel.bean2bean.dsl.api.converters;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * This class serves as test example of a converter that needs no parameters (or generator function)
 * Date: 16/02/19 - 18:15
 */
public class ArrayListGenerator implements Supplier<List> {
  @Override
  public List get() {
    return new ArrayList();
  }

  public static ArrayListGenerator create() {
    ArrayListGenerator generator = new ArrayListGenerator();
    return generator;
  }

}
