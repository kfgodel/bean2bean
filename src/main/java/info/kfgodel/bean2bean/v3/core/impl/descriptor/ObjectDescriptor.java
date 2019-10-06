package info.kfgodel.bean2bean.v3.core.impl.descriptor;

import info.kfgodel.bean2bean.v3.core.api.registry.Domain;
import info.kfgodel.reflect.types.descriptors.JavaTypeDescriptor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * This class represents a helper object to describe other instances so error messages have proper context
 *
 * Date: 10/03/19 - 18:38
 */
public class ObjectDescriptor {

  public String describeInstance(Object instance){
    if(instance == null){
      // We need to cover this special case first so the rest of the code can assume not null
      return "null";
    }
    if(instance.getClass().isArray()){
      return describeArray(instance);
    }
    String basicDescription = String.valueOf(instance);
    if (instance instanceof String) {
      return "\"" + basicDescription + "\"";
    }
    return basicDescription;
  }

  private String describeArray(Object instance) {
    if(instance instanceof long[]){
      return Arrays.toString((long[])instance);
    } else if(instance instanceof int[]){
      return Arrays.toString((int[])instance);
    } else if(instance instanceof short[]){
      return Arrays.toString((short[])instance);
    } else if(instance instanceof char[]){
      return Arrays.toString((char[])instance);
    } else if(instance instanceof byte[]){
      return Arrays.toString((byte[])instance);
    } else if(instance instanceof boolean[]){
      return Arrays.toString((boolean[])instance);
    } else if(instance instanceof float[]){
      return Arrays.toString((float[])instance);
    } else if(instance instanceof double[]){
      return Arrays.toString((double[])instance);
    }
    return Arrays.toString((Object[])instance);
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
