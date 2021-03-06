package info.kfgodel.bean2bean.v3.dsl.api.scopes;

import info.kfgodel.bean2bean.v3.core.api.registry.DomainVector;
import info.kfgodel.reflect.references.TypeRef;

import java.lang.reflect.Type;

/**
 * This type represents a configuration that limits the applicability of registered converters by scoping
 *  them with explicit input and output types, as the domain vector.<br>
 *
 * Date: 05/03/19 - 19:10
 */
public interface ExplicitScopeYetToBeDefinedDsl {

  /**
   * Indicates the type of acceptable input for the converter
   * @param inputType type of expected arguments
   * @return The partially defined configuration to complete the scope restriction
   */
  ExplicitScopeWithTypeSourceDsl accept(Type inputType);

  /**
   * Indicates the type of acceptable input by a class instance
   * @param inputType The class that represents the type
   * @param <I> Type of expected input
   * @return The partially defined configuration restricted to the input type
   */
  <I> ExplicitScopeWithParameterizedSourceDsl<I> accept(Class<I> inputType);

  /**
   * Indicates the type of acceptable input for the converter through a type reference
   * @param inputTypeRef Reference to the type of expected arguments
   * @param <I> Type of expected input
   * @return The partially defined configuration to complete the scope restriction
   */
  <I> ExplicitScopeWithParameterizedSourceDsl<I> accept(TypeRef<I> inputTypeRef);

  /**
   * Limits the applicability of a converter by using its type parameters to restrict its usage to valid arguments
   * and result.<br>
   * If no type parameters can be obtained using reflection on the lambda class, then {@link Object} will be used
   * instead for input or output types scoping. <br>
   * <br>
   * Note, this may collide with other converters if carelessly used. Use lambda refs like {@link info.kfgodel.bean2bean.v3.other.references.FunctionRef}
   * to explicitly define lambda parameter types<br>
   *
   * @return The partial definition of this configuration dsl scoping to implicit types
   */
  ImplicitScopeRegistrationDsl implicitTypes();

  /**
   * Indicates the explicit conversion vector fo which the converter can be used
   * @param conversionVector The vector that defines the scope of applicability for the converter
   * @return The configuration dsl to register the converter
   */
  ScopedRegistrationDsl vector(DomainVector conversionVector);
}
