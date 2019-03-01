package info.kfgodel.bean2bean.converters;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.CreationException;

import java.lang.reflect.Type;
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
    Collection targetCollection = createTargetCollection(task);

    return null;
  }

  private Collection createTargetCollection(Bean2beanTask task) {
    Type targetType = task.getTargetType();
    Object created = task.getDsl().generate().anInstanceOf(targetType);
    if (!(created instanceof Collection)) {
      throw new CreationException("Created instance["+ getClassOf(created)+"] can't be used as target collection", targetType);
    }
    return (Collection) created;
  }

  private String getClassOf(Object created) {
    return created == null ? "null" : created.getClass().getTypeName();
  }

  public static Collection2CollectionConverter create() {
    Collection2CollectionConverter converter = new Collection2CollectionConverter();
    return converter;
  }

}
