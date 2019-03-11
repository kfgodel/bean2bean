package info.kfgodel.bean2bean.core.impl.descriptor;

import info.kfgodel.bean2bean.core.api.registry.Domain;
import info.kfgodel.bean2bean.other.types.descriptors.JavaTypeDescriptor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.stream.Collectors;

/**
 * This class represents a helper object to describe other instances so error messages have proper context
 *
 * Date: 10/03/19 - 18:38
 */
public class ObjectDescriptor {

  public String describeInstance(Object instance){
    String basicDescription = String.valueOf(instance);
    if (instance instanceof String) {
      return "\"" + basicDescription + "\"";
    }
    return basicDescription;
  }

  public String describeType(Type targetType) {
    String basicTypeDescription = describeInstance(targetType);
    if ((targetType instanceof TypeVariable) || (targetType instanceof WildcardType)) {// For bounded types we add boundaries to the description so they are not just a single letter
      String boundsDescription = JavaTypeDescriptor.createFor(targetType)
        .getUpperBounds()
        .map(this::describeType)
        .collect(Collectors.joining(", ", " extends ", ""));
      return basicTypeDescription + boundsDescription;
    } else {
      return basicTypeDescription;
    }
  }

  public static ObjectDescriptor create() {
    ObjectDescriptor descriptor = new ObjectDescriptor();
    return descriptor;
  }

  public String describeSource(Object source, Domain sourceDomain) {
    String sourceDescription = this.describeInstance(source);
    return sourceDescription + " ∈ " + sourceDomain ;

  }

  public String describeTarget(Type targetType, Domain targetDomain) {
    if((targetType instanceof Class) || (targetType instanceof ParameterizedType)){
      // For classes the domain is sufficient for description
      return targetDomain.toString();
    }
    String targetTypeDescription = describeType(targetType);
    return targetDomain + " ⊇ " + targetTypeDescription;
//      targetTypeDescription + " ⊆ " + targetDomain;
  }

}
