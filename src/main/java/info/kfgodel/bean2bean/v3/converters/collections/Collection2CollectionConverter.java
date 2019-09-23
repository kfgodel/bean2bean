package info.kfgodel.bean2bean.v3.converters.collections;

import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.v3.core.api.exceptions.CreationException;
import info.kfgodel.bean2bean.v3.core.impl.descriptor.ObjectDescriptor;
import info.kfgodel.bean2bean.v3.other.types.extraction.TypeArgumentExtractor;

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
  public Collection apply(Collection sourceCollection, Bean2beanTask task) {
    Collection targetCollection = createTargetCollectionFor(task);
    Type expectedElementType = deduceExpectedElementTypeFor(task);
    addSourceElementsToTargetCollection(sourceCollection, targetCollection, expectedElementType, task);
    return targetCollection;
  }

  private void addSourceElementsToTargetCollection(Collection sourceCollection, Collection targetCollection, Type expectedElementType, Bean2beanTask task) {
    for (Object sourceElement : sourceCollection) {
      Object targetElement = task.getDsl().convert().from(sourceElement).to(expectedElementType);
      targetCollection.add(targetElement);
    }
  }

  private Type deduceExpectedElementTypeFor(Bean2beanTask task) {
    Type targetType = task.getTargetType();
    Type elementType = TypeArgumentExtractor.create().getArgumentUsedFor(Collection.class, targetType)
      .orElse(Object.class);
    return elementType;
  }

  private Collection createTargetCollectionFor(Bean2beanTask task) {
    Type targetType = task.getTargetType();
    Object created = task.getDsl().generate().anInstanceOf(targetType);
    if (!(created instanceof Collection)) {
      String typeDescription = ObjectDescriptor.create().describeType(created.getClass());
      throw new CreationException("Created instance of type["+ typeDescription +"] can't be used as target collection", targetType);
    }
    return (Collection) created;
  }

  public static Collection2CollectionConverter create() {
    Collection2CollectionConverter converter = new Collection2CollectionConverter();
    return converter;
  }

}
