package info.kfgodel.bean2bean.core.api;

import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.other.types.descriptors.JavaTypeDescriptor;

import java.lang.reflect.Type;

/**
 * This type represents a task that can be processed by a {@link Bean2bean} core
 * Date: 12/02/19 - 00:08
 */
public interface Bean2beanTask {
  /**
   * The vector that indicates from which domain to which codomain this task generates a result
   * @return A domain vector indicating the direction for a conversion
   */
  DomainVector getConversionVector();

  /**
   * The object that is the source of the conversion from which a result must be generated
   * @return The input for the conversion
   */
  Object getSource();

  /**
   * The type of result this task is expected to produce
   * @return The type that indicates the expected output
   */
  Type getTargetType();

  /**
   * Returns a type descriptor that has more method to interact with the target type
   * than the built-in {@link Type}
   * @return The type descriptor for the target type
   */
  default JavaTypeDescriptor getTargetTypeDescriptor(){
    return JavaTypeDescriptor.createFor(getTargetType());
  }

  /**
   * Returns the dsl instance that is contextual to this task and can be used for nesting tasks
   * @return The dsl to ease task definitions
   */
  B2bDsl getDsl();

  /**
   * Generates a nested conversion for the given object into the expected type.<br>
   *   Impl. note: This method may be temporary since a richer dsl may be needed (instead of only this)
   * @param sourceElement The object to use as source for the conversion
   * @param expectedElementType The type of expected result after the conversion
   * @return The converted object result
   */
  Object nestConversionFrom(Object sourceElement, Type expectedElementType);

  /**
   * Provides a simple string representation of the source object to be used on messages
   * @return A standard representation of the source
   */
  String describeSource();

  /**
   * Provides a simple string representation of the target type to be used on messages
   * @return A standard representation of the target type
   */
  String describeTarget();
}
