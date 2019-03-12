package info.kfgodel.bean2bean.dsl.api.scopes;

import info.kfgodel.bean2bean.other.references.TypeRef;

import java.lang.reflect.Type;

/**
 * This type represents the parameterized interface for source defined scopes that need to define a target domain
 * Date: 11/03/19 - 21:41
 */
public interface ExplicitScopeWithParameterizedSourceDsl<I> {

  /**
   * Completes the scope by defining the target type for the converter.<br>
   *   The registered converter should produce instances of the given type
   * @param targetType The type of instances to expect out of the converter
   * @return The configuration dsl to register the scoped converter
   */
  <O> ParameterizedScopeDsl<I,O> andProduce(Class<O> targetType);

  /**
   * Completes the scope by defining the target type for the converter.<br>
   *   The registered converter should produce instances of the given type
   * @param targetType The type of instances to expect out of the converter
   * @return The configuration dsl to register the scoped converter
   */
  <O> ParameterizedScopeDsl<I,O> andProduce(Type targetType);

  /**
   * Completes the scope by defining the target type for the converter.<br>
   *   The registered converter should produce instances of the given type
   * @param targetTypeRef The reference for the type of instances to expect out of the converter
   * @return The configuration dsl to register the scoped converter
   */
  <O> ParameterizedScopeDsl<I,O> andProduce(TypeRef<O> targetTypeRef);
}
