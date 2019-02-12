package info.kfgodel.bean2bean.core.impl.conversion;

import java.util.function.Function;

/**
 * This type represents the process needed to make an object conversion
 * Date: 12/02/19 - 01:18
 */
public class ObjectConversionProcess implements Function<ObjectConversion, Object> {

  private Function converter;

  @Override
  public Object apply(ObjectConversion objectConversion) {
    Object input = objectConversion.getSource();
    Object output = converter.apply(input);
    return output;
  }

  public static ObjectConversionProcess create(Function converter) {
    ObjectConversionProcess process = new ObjectConversionProcess();
    process.converter = converter;
    return process;
  }

}
