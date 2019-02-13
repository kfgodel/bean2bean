package info.kfgodel.bean2bean.core.impl.registry;

import info.kfgodel.bean2bean.other.TypeVector;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * This type extracts the implicit type vector from a function instance
 * Date: 12/02/19 - 00:56
 */
public class VectorExtractor implements Function<Function, TypeVector> {
  @Override
  public TypeVector apply(Function function) {
    Class<? extends Function> functionClass = function.getClass();
    ParameterizedType genericFunctionInterface = (ParameterizedType) functionClass.getGenericInterfaces()[0];
    Type[] typeArguments = genericFunctionInterface.getActualTypeArguments();
    Type inputType = typeArguments[0];
    Type outputType = typeArguments[1];
    return TypeVector.create(inputType, outputType);
  }

  public static VectorExtractor create() {
    VectorExtractor extractor = new VectorExtractor();
    return extractor;
  }

}
