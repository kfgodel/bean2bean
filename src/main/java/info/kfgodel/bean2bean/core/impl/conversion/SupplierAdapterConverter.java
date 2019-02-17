package info.kfgodel.bean2bean.core.impl.conversion;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class serves as an adapter between a supplier function and the converter interface
 * Date: 16/02/19 - 18:33
 */
public class SupplierAdapterConverter implements Function<ObjectConversion, Object> {

  private Supplier function;

  @Override
  public Object apply(ObjectConversion objectConversion) {
    return function.get();
  }

  public static SupplierAdapterConverter create(Supplier function) {
    SupplierAdapterConverter converter = new SupplierAdapterConverter();
    converter.function = function;
    return converter;
  }

}