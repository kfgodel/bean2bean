package info.kfgodel.bean2bean.core.impl.registry.domains;

import info.kfgodel.bean2bean.core.api.registry.Domain;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.other.BiFunctionRef;
import info.kfgodel.bean2bean.other.ConsumerRef;
import info.kfgodel.bean2bean.other.FunctionRef;
import info.kfgodel.bean2bean.other.SupplierRef;

import javax.lang.model.type.NullType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This type extracts the implicit type vector from a function instance
 * Date: 12/02/19 - 00:56
 */
public class DomainVectorExtractor {

  private DomainCalculator calculator;

  public DomainVector extractFrom(Consumer function) {
    Type[] typeArguments = getTypeArguments(function.getClass());
    Type inputType = typeArguments[0];
    // Implicit null result
    return createVectorFor(inputType, NullType.class);
  }

  public DomainVector extractFrom(ConsumerRef function) {
    Type inputType = function.getInputType();
    return createVectorFor(inputType, NullType.class);
  }

  public DomainVector extractFrom(Supplier function) {
    Type[] typeArguments = getTypeArguments(function.getClass());
    Type outputType = typeArguments[0];
    // null is implicit when supplier is thought as a function
    return createVectorFor(NullType.class, outputType);
  }

  public DomainVector extractFrom(SupplierRef<?> converterFunction) {
    return createVectorFor(NullType.class, converterFunction.getOutputType());
  }

  public DomainVector extractFrom(Function function) {
    return extractFromGenericInterface(function.getClass(), 1);
  }

  public DomainVector extractFrom(FunctionRef<?, ?> converterFunctionRef) {
    Type inputType = converterFunctionRef.getInputType();
    Type outputType = converterFunctionRef.getOutputType();
    return createVectorFor(inputType, outputType);
  }

  public DomainVector extractFrom(BiFunction<?, B2bDsl, ?> biFunction) {
    return extractFromGenericInterface(biFunction.getClass(), 2);
  }

  public DomainVector extractFrom(BiFunctionRef<?, B2bDsl, ?> converterFunction) {
    Type outputType = converterFunction.getOutputType();
    Type firstInputType = converterFunction.getFirstInputType();
    return createVectorFor(firstInputType, outputType);
  }

  /**
   * Poor implementation, change later
   */
  private DomainVector extractFromGenericInterface(Class<?> functionClass, int outputTypeIndex) {
    Type[] typeArguments = getTypeArguments(functionClass);
    Type inputType = typeArguments[0];
    Type outputType = typeArguments[outputTypeIndex];
    return createVectorFor(inputType, outputType);
  }

  private Type[] getTypeArguments(Class<?> functionClass) {
    ParameterizedType genericFunctionInterface = (ParameterizedType) functionClass.getGenericInterfaces()[0];
    return genericFunctionInterface.getActualTypeArguments();
  }

  private DomainVector createVectorFor(Type inputType, Type outputType) {
    return DomainVector.create(domainFor(inputType), domainFor(outputType));
  }

  private Domain domainFor(Type javaType) {
    return calculator.forType(javaType);
  }

  public static DomainVectorExtractor create(DomainCalculator calculator) {
    DomainVectorExtractor extractor = new DomainVectorExtractor();
    extractor.calculator = calculator;
    return extractor;
  }

}
