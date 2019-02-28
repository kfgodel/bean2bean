package info.kfgodel.bean2bean.converters;

import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;

import java.util.Collection;
import java.util.function.BiFunction;

/**
 * This class implements a collection to collection converter that instantiates desired collection
 * using b2b creator converters and converts each element to the expected type
 *
 * Date: 28/02/19 - 19:42
 */
public class Collection2CollectionConverter implements BiFunction<ObjectConversion, B2bDsl, Collection> {

  @Override
  public Collection apply(ObjectConversion objectConversion, B2bDsl b2bDsl) {
    return null;
  }

  public static Collection2CollectionConverter create() {
    Collection2CollectionConverter converter = new Collection2CollectionConverter();
    return converter;
  }

}
