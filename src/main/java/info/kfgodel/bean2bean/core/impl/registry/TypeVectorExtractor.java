package info.kfgodel.bean2bean.core.impl.registry;

import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.other.BiFunctionRef;
import info.kfgodel.bean2bean.other.FunctionRef;
import info.kfgodel.bean2bean.other.SupplierRef;
import info.kfgodel.bean2bean.other.TypeVector;

import javax.lang.model.type.NullType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This type extracts the implicit type vector from a function instance
 * Date: 12/02/19 - 00:56
 */
public class TypeVectorExtractor {

  public TypeVector extractFrom(Supplier function) {
    Type[] typeArguments = getTypeArguments(function.getClass());
    Type outputType = typeArguments[0];
    // null is implicit when supplier is thought as a function
    return TypeVector.create(NullType.class, outputType);
  }

  public TypeVector extractFrom(SupplierRef<?> converterFunction) {
    return TypeVector.create(NullType.class, converterFunction.getOutputType());
  }

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
    Type[] typeArguments = getTypeArguments(functionClass);
    Type inputType = typeArguments[0];
    Type outputType = typeArguments[outputTypeIndex];
    return TypeVector.create(inputType, outputType);
  }

  private Type[] getTypeArguments(Class<?> functionClass) {
    ParameterizedType genericFunctionInterface = (ParameterizedType) functionClass.getGenericInterfaces()[0];
    return genericFunctionInterface.getActualTypeArguments();
  }

  public TypeVector extractFrom(FunctionRef<?, ?> converterFunctionRef) {
    Type inputType = converterFunctionRef.getInputType();
    Type outputType = converterFunctionRef.getOutputType();
    return TypeVector.create(inputType, outputType);
  }

  public static TypeVectorExtractor create() {
    TypeVectorExtractor extractor = new TypeVectorExtractor();
    return extractor;
  }

  public TypeVector extractFrom(BiFunctionRef<?, B2bDsl, ?> converterFunction) {
    Type outputType = converterFunction.getOutputType();
    Type firstInputType = converterFunction.getFirstInputType();
    return TypeVector.create(firstInputType, outputType);
  }
}
