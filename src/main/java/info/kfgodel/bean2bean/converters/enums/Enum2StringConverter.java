package info.kfgodel.bean2bean.converters.enums;

import java.util.function.Function;

/**
 * This class converts enums into their name
 * Date: 12/03/19 - 21:53
 */
public class Enum2StringConverter implements Function<Enum, String> {

  @Override
  public String apply(Enum source) {
    return source.name();
  }

  public static Enum2StringConverter create() {
    Enum2StringConverter converter = new Enum2StringConverter();
    return converter;
  }

}
