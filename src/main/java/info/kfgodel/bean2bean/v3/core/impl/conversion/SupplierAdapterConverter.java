package info.kfgodel.bean2bean.v3.core.impl.conversion;

import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class serves as an adapter between a supplier function and the converter interface
 * Date: 16/02/19 - 18:33
 */
public class SupplierAdapterConverter implements Function<Bean2beanTask, Object> {

  private Supplier<Object> function;

  @Override
  public Object apply(Bean2beanTask objectConversion) {
    return function.get();
  }

  @Override
  public String toString() {
    return "SupplierAdapterConverter{" +
      "function=" + function +
      '}';
  }

  public static SupplierAdapterConverter create(Supplier<?> function) {
    SupplierAdapterConverter converter = new SupplierAdapterConverter();
    converter.function = (Supplier<Object>) function;
    return converter;
  }

}
