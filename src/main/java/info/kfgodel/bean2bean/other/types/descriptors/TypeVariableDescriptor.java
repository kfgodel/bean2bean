package info.kfgodel.bean2bean.other.types.descriptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Optional;

/**
 * This class represents the descriptor for type variable types.<br>
 *   Type variables are usually used in container class to parameterize them, and
 *   have single upper case letter names.
 * Date: 09/03/19 - 16:20
 */
public class TypeVariableDescriptor extends GeneralTypeDescriptor {
  public static Logger LOG = LoggerFactory.getLogger(TypeVariableDescriptor.class);

  private TypeVariable typeVariable;

  @Override
  public Optional<Class> getAssignableClass() {
    Type[] upperBounds = typeVariable.getBounds();
    return calculateAssignableClassFromUpperBounds(upperBounds, getType());
  }

  public static Optional<Class> calculateAssignableClassFromUpperBounds(Type[] upperBounds, Type type) {
    Type[] bounds = Optional.ofNullable(upperBounds)
      .orElse(NO_TYPES); // Just in case the api returns null
    if(bounds.length > 1){
      LOG.warn("Type[{}] has multiple bounds {}. Only the first will be considered for assignments", type, Arrays.toString(bounds));
    }
    return Arrays.stream(bounds)
      .map(JavaTypeDescriptor::createFor)
      .findFirst() // We use only the first bound as assignable type (this may be an error but we don't know how to deal with multi bounds yet
      .flatMap(JavaTypeDescriptor::getAssignableClass);
  }

  public static TypeVariableDescriptor create(TypeVariable typeVariable) {
    TypeVariableDescriptor descriptor = new TypeVariableDescriptor();
    descriptor.typeVariable = typeVariable;
    return descriptor;
  }

}
