package info.kfgodel.bean2bean.converters;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.CreationException;
import info.kfgodel.bean2bean.other.types.descriptors.JavaTypeDescriptor;

import javax.lang.model.type.NullType;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * This class implements a generic class instantiator that can create instances of any class
 * that has a niladic constructor
 *
 * Date: 05/03/19 - 22:09
 */
public class GenericInstantiator implements BiFunction<NullType, Bean2beanTask, Object> {

  @Override
  public Object apply(NullType nullType, Bean2beanTask task) {
    Type targetType = task.getTargetType();
    Optional<Class> instantiableClass = deduceInstantiableClassFrom(targetType);
    return instantiableClass
      .map(clase -> createInstanceFrom(clase, targetType))
      .orElseThrow(()-> new CreationException("Type["+targetType+"] is not instantiable using a niladic constructor", targetType));
  }

  private Optional<Class> deduceInstantiableClassFrom(Type targetType) {
    JavaTypeDescriptor typeDescriptor = JavaTypeDescriptor.createFor(targetType);
    return typeDescriptor.getInstantiableClass();
  }

  public Object createInstanceFrom(final Class clase, Type targetType) throws CreationException {
    try {
      return clase.newInstance();
    } catch (final InstantiationException e) {
      throw new CreationException("Class["+clase+"] is not instantiable using a niladic constructor", targetType, e);
    } catch (final IllegalAccessException e) {
      throw new CreationException("Class constructor can't be accessed: " + e.getMessage(), targetType, e);
    } catch (final ExceptionInInitializerError e) {
      throw new CreationException("Class initialization failed while instanting: " + clase, targetType, e);
    }
  }

  public static GenericInstantiator create() {
    GenericInstantiator instantiator = new GenericInstantiator();
    return instantiator;
  }
}
