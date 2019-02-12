package info.kfgodel.bean2bean.dsl.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Example converter for test
 * Date: 12/02/19 - 20:41
 */
public class ArrayListToListOfStringsConverter implements Function<ArrayList,List<String>> {

  @Override
  public List<String> apply(ArrayList list) {
    return ((Stream<Object>) list.stream())
      .map(String::valueOf)
      .collect(Collectors.toList());
  }

  public static ArrayListToListOfStringsConverter create() {
    ArrayListToListOfStringsConverter converter = new ArrayListToListOfStringsConverter();
    return converter;
  }

}
