package info.kfgodel.bean2bean.converters;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;

import java.util.Collection;
import java.util.function.BiFunction;

/**
 * This class implements a collection to collection converter that instantiates desired collection
 * using b2b creator converters and converts each element to the expected type
 *
 * Date: 28/02/19 - 19:42
 */
public class Collection2CollectionConverter implements BiFunction<Collection, Bean2beanTask, Collection> {

  @Override
  public Collection apply(Collection input, Bean2beanTask task) {
    Collection targetCollection = task.getDsl().generate().anInstanceOf(task.getTargetType());
    return null;
  }

  public static Collection2CollectionConverter create() {
    Collection2CollectionConverter converter = new Collection2CollectionConverter();
    return converter;
  }

}
