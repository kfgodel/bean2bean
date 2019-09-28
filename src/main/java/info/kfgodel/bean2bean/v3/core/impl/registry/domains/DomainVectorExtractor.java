package info.kfgodel.bean2bean.v3.core.impl.registry.domains;

import info.kfgodel.bean2bean.v3.core.api.registry.Domain;
import info.kfgodel.bean2bean.v3.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.v3.dsl.api.Nothing;
import info.kfgodel.bean2bean.v3.other.references.BiFunctionRef;
import info.kfgodel.bean2bean.v3.other.references.ConsumerRef;
import info.kfgodel.bean2bean.v3.other.references.FunctionRef;
import info.kfgodel.bean2bean.v3.other.references.SupplierRef;
import info.kfgodel.reflect.types.extraction.TypeArgumentExtractor;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * This type extracts the implicit type vector from a function instance
 * Date: 12/02/19 - 00:56
 */
public class DomainVectorExtractor {

  private DomainCalculator calculator;
  private TypeArgumentExtractor typeExtractor;

  public DomainVector extractFrom(Consumer function) {
    Type inputType = typeExtractor.getArgumentUsedFor(Consumer.class, function.getClass())
      .orElse(Object.class);
    // Implicit null result
    return createVectorFor(inputType, Nothing.class);
  }

  public DomainVector extractFrom(ConsumerRef function) {
    Type inputType = function.getInputType();
    return createVectorFor(inputType, Nothing.class);
  }

  public DomainVector extractFrom(Supplier function) {
    Type outputType = typeExtractor.getArgumentUsedFor(Supplier.class, function.getClass())
      .orElse(Object.class);
    // null is implicit when supplier is thought as a function
    return createVectorFor(Nothing.class, outputType);
  }

  public DomainVector extractFrom(SupplierRef<?> converterFunction) {
    Type outputType = converterFunction.getOutputType();
    return createVectorFor(Nothing.class, outputType);
  }

  public DomainVector extractFrom(Function function) {
    List<Type> arguments = typeExtractor.getArgumentsUsedFor(Function.class, function.getClass())
      .collect(Collectors.toList());
    Type inputType = arguments.isEmpty() ? Object.class : arguments.get(0);
    Type outputType = arguments.isEmpty() ? Object.class : arguments.get(1);
    return createVectorFor(inputType, outputType);
  }

  public DomainVector extractFrom(FunctionRef<?, ?> converterFunctionRef) {
    Type inputType = converterFunctionRef.getInputType();
    Type outputType = converterFunctionRef.getOutputType();
    return createVectorFor(inputType, outputType);
  }

  public DomainVector extractFrom(BiFunction<?, ?, ?> biFunction) {
    List<Type> arguments = typeExtractor.getArgumentsUsedFor(BiFunction.class, biFunction.getClass())
      .collect(Collectors.toList());
    Type inputType = arguments.isEmpty() ? Object.class : arguments.get(0);
    Type outputType = arguments.isEmpty() ? Object.class : arguments.get(2);
    return createVectorFor(inputType, outputType);
  }

  public DomainVector extractFrom(BiFunctionRef<?, ?, ?> converterFunction) {
    Type outputType = converterFunction.getOutputType();
    Type firstInputType = converterFunction.getFirstInputType();
    return createVectorFor(firstInputType, outputType);
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
    extractor.typeExtractor = TypeArgumentExtractor.create();
    return extractor;
  }

}
