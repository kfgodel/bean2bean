package info.kfgodel.bean2bean.dsl.api.scopes;

import info.kfgodel.bean2bean.other.references.TypeRef;

import java.lang.reflect.Type;

/**
 * This type represents a partial configuration where the source type of an explicit scope is defined
 * and the target is yet to be defined
 *
 * Date: 05/03/19 - 19:15
 */
public interface ExplicitScopeWithTypeSourceDsl {

  /**
   * Completes the scope by defining the target type for the converter.<br>
   *   The registered converter should produce instances of the given type
   * @param targetType The type of instances to expect out of the converter
   * @return The configuration dsl to register the scoped converter
   */
  ScopeDsl andProduce(Type targetType);

  /**
   * Completes the scope by defining the target type for the converter.<br>
   *   The registered converter should produce instances of the given type
   * @param targetTypeRef The reference for the type of instances to expect out of the converter
   * @return The configuration dsl to register the scoped converter
   */
  default ScopeDsl andProduce(TypeRef<?> targetTypeRef){
    return andProduce(targetTypeRef.getReference());
  }
}