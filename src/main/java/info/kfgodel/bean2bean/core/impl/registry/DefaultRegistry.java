package info.kfgodel.bean2bean.core.impl.registry;

import info.kfgodel.bean2bean.core.api.registry.Bean2BeanRegistry;
import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;
import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversionProcess;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * This type implements the default bean2bean registry
 * Date: 12/02/19 - 00:30
 */
public class DefaultRegistry implements Bean2BeanRegistry {

  private Map<TypeVector, Function> processByVector;

  public static DefaultRegistry create() {
    DefaultRegistry registry = new DefaultRegistry();
    registry.processByVector = new HashMap<>();
    return registry;
  }

  @Override
  public <I extends ObjectConversion, O> Optional<Function<I, O>> findBestProcessFor(I input) {
    TypeVector vector = input.getConversionVector();
    Optional<Function<I, O>> found = Optional.ofNullable(processByVector.get(vector));
    return found;
  }

  @Override
  public Bean2BeanRegistry store(Function<?, ?> converter) {
    TypeVector implicitVector = VectorExtractor.create().apply(converter);
    ObjectConversionProcess process = ObjectConversionProcess.create(converter);
    this.processByVector.put(implicitVector, process);
    return this;
  }
}
