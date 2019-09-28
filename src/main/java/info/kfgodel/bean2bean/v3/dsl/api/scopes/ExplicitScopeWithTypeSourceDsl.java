package info.kfgodel.bean2bean.v3.dsl.api.scopes;

import info.kfgodel.reflect.references.TypeRef;

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
  ScopedRegistrationDsl andProduce(Type targetType);

  /**
   * Completes the scope by defining the target type for the converter.<br>
   *   The registered converter should produce instances of the given type
   * @param targetTypeRef The reference for the type of instances to expect out of the converter
   * @return The configuration dsl to register the scoped converter
   */
  default ScopedRegistrationDsl andProduce(TypeRef<?> targetTypeRef){
    return andProduce(targetTypeRef.getReference());
  }
}
