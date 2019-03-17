package info.kfgodel.bean2bean.converters;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.function.BiFunction;

/**
 * This class represents a converter from an array instance into a another array instance by converting
 * each array element as needed
 *
 * Date: 03/03/19 - 19:38
 */
public class Array2ArrayConverter implements BiFunction<Object, Bean2beanTask, Object> {

  @Override
  public Object apply(Object source, Bean2beanTask task) {
    int sourceSize = calculateSourceSize(source, task);
    Object created = task.getDsl().convert().from(sourceSize).to(task.getTargetType());
    Class expectedElementType = calculateElementTypeFrom(created);
    addSourceElementToTargetArray(source, sourceSize, created, expectedElementType, task);
    return created;
  }

  private void addSourceElementToTargetArray(Object source, int sourceSize, Object created, Type expectedElementType, Bean2beanTask task) {
    for (int i = 0; i < sourceSize; i++) {
      Object sourceElement = Array.get(source, i);
      Object targetElement = task.getDsl().convert().from(sourceElement).to(expectedElementType);
      Array.set(created, i, targetElement);
    }
  }

  private Class calculateElementTypeFrom(Object created) {
    return created.getClass().getComponentType();
  }

  private int calculateSourceSize(Object source, Bean2beanTask task) {
    try {
      return Array.getLength(source);
    } catch (NullPointerException e) {
      throw new ConversionException("Source is not an array: null", task, e);
    } catch (IllegalArgumentException e) {
      throw new ConversionException("Source is not an array: " + task.describeSource(), task, e);
    }
  }

  public static Array2ArrayConverter create() {
    Array2ArrayConverter converter = new Array2ArrayConverter();
    return converter;
  }

  public static boolean shouldBeUsed(DomainVector domainVector) {
    String sourceDomainName = domainVector.getSource().getName();
    if(!sourceDomainName.endsWith("[]")){
      // Input is not an array
      return false;
    }
    String targetDomainName = domainVector.getTarget().getName();
    if(!targetDomainName.endsWith("[]")){
      // Output is not an array
      return false;
    }
    return true;
  }
}
