package info.kfgodel.bean2bean.converters;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.CreationException;

import java.util.Optional;
import java.util.function.BiFunction;

/**
 * This class implements a generic class instantiator that can create instances of any class
 * that has a niladic constructor
 *
 * Date: 05/03/19 - 22:09
 */
public class GenericInstantiator implements BiFunction<Void, Bean2beanTask, Object> {

  @Override
  public Object apply(Void voidInput, Bean2beanTask task) {
    Optional<Class> instantiableClass = task.getTargetTypeDescriptor().getInstantiableClass();
    return instantiableClass
      .map(clase -> createInstanceFrom(clase, task))
      .orElseThrow(()-> new CreationException("Type["+task.getTargetType()+"] is not instantiable using a niladic constructor", task.getTargetType()));
  }

  public Object createInstanceFrom(final Class clase, Bean2beanTask task) throws CreationException {
    try {
      return clase.newInstance();
    } catch (final InstantiationException e) {
      throw new CreationException("Class["+clase+"] is not instantiable using a niladic constructor", task.getTargetType(), e);
    } catch (final IllegalAccessException e) {
      throw new CreationException("Class constructor can't be accessed: " + e.getMessage(), task.getTargetType(), e);
    } catch (final ExceptionInInitializerError e) {
      throw new CreationException("Class initialization failed while instanting: " + clase, task.getTargetType(), e);
    }
  }

  public static GenericInstantiator create() {
    GenericInstantiator instantiator = new GenericInstantiator();
    return instantiator;
  }
}
