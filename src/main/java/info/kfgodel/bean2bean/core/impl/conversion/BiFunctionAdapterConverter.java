package info.kfgodel.bean2bean.core.impl.conversion;

import info.kfgodel.bean2bean.dsl.api.B2bDsl;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * This type represents the converter that delegates on a bifunction to do the actual conversion
 * and passes a b2b dsl instance to it
 *
 * Date: 16/02/19 - 14:30
 */
public class BiFunctionAdapterConverter implements Function<ObjectConversion, Object> {

  private B2bDsl b2bDsl;
  private BiFunction biFunction;

  @Override
  public Object apply(ObjectConversion objectConversion) {
    Object input = objectConversion.getSource();
    Object output = biFunction.apply(input, b2bDsl);
    return output;
  }

  public static BiFunctionAdapterConverter create(BiFunction function, B2bDsl b2bDsl) {
    BiFunctionAdapterConverter converter = new BiFunctionAdapterConverter();
    converter.biFunction = function;
    converter.b2bDsl = b2bDsl;
    return converter;
  }

}
