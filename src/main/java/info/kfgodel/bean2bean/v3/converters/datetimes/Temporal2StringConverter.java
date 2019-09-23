package info.kfgodel.bean2bean.v3.converters.datetimes;

import java.time.temporal.Temporal;
import java.util.function.Function;

/**
 * This class converts any temporal instance into its ISO representation
 * Date: 23/03/19 - 18:22
 */
public class Temporal2StringConverter implements Function<Temporal,String> {
  @Override
  public String apply(Temporal temporal) {
    return temporal.toString();
  }

  public static Temporal2StringConverter create() {
    Temporal2StringConverter converter = new Temporal2StringConverter();
    return converter;
  }

}
