package info.kfgodel.bean2bean.core.impl.registry;

import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.other.TypeVector;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * This type extracts the implicit type vector from a function instance
 * Date: 12/02/19 - 00:56
 */
public class VectorExtractor {

  public TypeVector extractFrom(Function function) {
    return extractFromGenericInterface(function.getClass(), 1);
  }

  public TypeVector extractFrom(BiFunction<?, B2bDsl, ?> biFunction) {
    return extractFromGenericInterface(biFunction.getClass(), 2);
  }

  /**
   * Poor implementation, change later
   */
  private TypeVector extractFromGenericInterface(Class<?> functionClass, int outputTypeIndex) {
    ParameterizedType genericFunctionInterface = (ParameterizedType) functionClass.getGenericInterfaces()[0];
    Type[] typeArguments = genericFunctionInterface.getActualTypeArguments();
    Type inputType = typeArguments[0];
    Type outputType = typeArguments[outputTypeIndex];
    return TypeVector.create(inputType, outputType);
  }


  public static VectorExtractor create() {
    VectorExtractor extractor = new VectorExtractor();
    return extractor;
  }

}
