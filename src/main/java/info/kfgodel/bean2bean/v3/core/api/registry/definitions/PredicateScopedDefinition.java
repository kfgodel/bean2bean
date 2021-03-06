package info.kfgodel.bean2bean.v3.core.api.registry.definitions;

import info.kfgodel.bean2bean.v3.core.api.registry.DomainVector;

import java.util.function.Predicate;

/**
 * This type represents a converter definition that is scoped by a predicate
 * Date: 19/02/19 - 22:15
 */
public interface PredicateScopedDefinition extends ConverterDefinition {

  /**
   * Return the predicate that defines the applicability of the converted based
   * on the conversion vector that is needed.<br>
   * This predicate will define if the converter can be used on a conversion for the given vector
   *
   * @return The predicate that implicitly defines the scope of the converter
   */
  Predicate<DomainVector> getScopePredicate();

}
