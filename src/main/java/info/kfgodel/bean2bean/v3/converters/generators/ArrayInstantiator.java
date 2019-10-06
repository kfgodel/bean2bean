package info.kfgodel.bean2bean.v3.converters.generators;

import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.v3.core.api.exceptions.CreationException;
import info.kfgodel.bean2bean.v3.core.api.registry.DomainVector;
import info.kfgodel.reflect.types.descriptors.JavaTypeDescriptor;

import java.lang.reflect.Array;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * This class implements the converter from a integer to an array instantiating it with the indicated integer size
 * Date: 03/03/19 - 17:32
 */
public class ArrayInstantiator implements BiFunction<Integer, Bean2beanTask, Object> {

  @Override
  public Object apply(Integer expectedArraySize, Bean2beanTask task) {
    Class elementType = deduceElementTypeFrom(task);
    Object createdArray = Array.newInstance(elementType, expectedArraySize);
    return createdArray;
  }

  private Class deduceElementTypeFrom(Bean2beanTask task) {
    JavaTypeDescriptor descriptor = task.getTargetTypeDescriptor();
    Optional<Class> componentType  = descriptor.getComponentType();
    return componentType
      .orElseThrow(()-> new CreationException("Can't instantiate array for non array type: " + task.getTargetType(), task.getTargetType()));
  }

  public static ArrayInstantiator create() {
    ArrayInstantiator instantiator = new ArrayInstantiator();
    return instantiator;
  }

  public static boolean shouldBeUsed(DomainVector domainVector) {
    String sourceDomainName = domainVector.getSource().getName();
    if(!sourceDomainName.equals(Integer.class.getTypeName())){
      // Input is an integer
      return false;
    }
    String targetDomainName = domainVector.getTarget().getName();
    if(!targetDomainName.endsWith("[]")){
      // Output is an array
      return false;
    }
    return true;
  }
}
