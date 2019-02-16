package info.kfgodel.bean2bean.dsl.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This type serves as an example of nesting conversions inside converters to delegate
 * parts of the conversion to b2b
 * Date: 16/02/19 - 13:35
 */
public class ArrayListToListOfStringsNestedConverter implements BiFunction<ArrayList, B2bDsl, List<String>> {

  @Override
  public List<String> apply(ArrayList arrayList, B2bDsl b2bDsl) {
    Stream<Integer> inputStream = arrayList.stream();
    return inputStream
      .map(value -> b2bDsl.convert().from(value).to(String.class))
      .collect(Collectors.toList());
  }

  public static ArrayListToListOfStringsNestedConverter create() {
    ArrayListToListOfStringsNestedConverter converter = new ArrayListToListOfStringsNestedConverter();
    return converter;
  }

}
