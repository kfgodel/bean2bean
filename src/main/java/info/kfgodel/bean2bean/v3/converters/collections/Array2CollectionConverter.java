package info.kfgodel.bean2bean.v3.converters.collections;

import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.v3.core.api.exceptions.CreationException;
import info.kfgodel.bean2bean.v3.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.v3.core.impl.descriptor.ObjectDescriptor;
import info.kfgodel.reflect.types.extraction.TypeArgumentExtractor;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.function.BiFunction;

/**
 * This type converts from any array to an expected collection
 * Date: 17/03/19 - 15:20
 */
public class Array2CollectionConverter implements BiFunction<Object, Bean2beanTask, Collection> {
  @Override
  public Collection apply(Object source, Bean2beanTask task) {
    Collection targetCollection = createTargetCollectionFor(task);
    Type expectedElementType = deduceExpectedElementTypeFor(task);
    addArrayElementsToCollection(source, expectedElementType, targetCollection, task);
    return targetCollection;
  }

  private void addArrayElementsToCollection(Object source, Type expectedElementType, Collection targetCollection, Bean2beanTask task) {
    final int arrayLength = Array.getLength(source);
    for (int i = 0; i < arrayLength; i++) {
      Object sourceElement = Array.get(source, i);
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

  public static Array2CollectionConverter create() {
    Array2CollectionConverter converter = new Array2CollectionConverter();
    return converter;
  }

  public static boolean shouldBeUsed(DomainVector domainVector) {
    String sourceDomainName = domainVector.getSource().getName();
    if(!sourceDomainName.endsWith("[]")){
      // Input is not an array
      return false;
    }
    boolean aCollectionIsExpected = domainVector.getTarget().getHierarchy()
      .anyMatch(targetDomain -> targetDomain.getName().equals(Collection.class.getTypeName()));
    return aCollectionIsExpected;
  }
}
