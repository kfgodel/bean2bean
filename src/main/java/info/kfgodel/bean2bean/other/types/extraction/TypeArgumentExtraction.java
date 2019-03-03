package info.kfgodel.bean2bean.other.types.extraction;

import info.kfgodel.bean2bean.other.types.SupertypeSpliterator;
import info.kfgodel.bean2bean.other.types.descriptors.JavaTypeDescriptor;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * This class represents a single type argument extraction which needs to carry all the
 * type variable replacement with their actual values
 * Date: 28/02/19 - 23:31
 */
public class TypeArgumentExtraction {

  private Type concreteType;
  private Map<Type, Type[]> argumentsByType;

  public static TypeArgumentExtraction create(Type concreteType) {
    TypeArgumentExtraction extraction = new TypeArgumentExtraction();
    extraction.concreteType = concreteType;
    extraction.argumentsByType = new LinkedHashMap<>();
    extraction.calculateTypeArguments();
    return extraction;
  }

  private void calculateTypeArguments() {
    SupertypeSpliterator.createAsStream(concreteType)
      .map(JavaTypeDescriptor::createFor)
      .forEach(this::discoverActualArgumentsFor);
  }

  private void discoverActualArgumentsFor(JavaTypeDescriptor typeDescriptor) {
    Type[] typeArguments = typeDescriptor.getTypeArguments();
    registerArguments(typeDescriptor, typeArguments);
  }

  private void registerArguments(JavaTypeDescriptor typeDescriptor, Type[] typeArguments) {
    Type aType = typeDescriptor.getType();
    Type[] registeredArguments = argumentsByType.putIfAbsent(aType, typeArguments);
    if(registeredArguments != null){
      // No need to run the rest, the hierarchy was aleready registered
      return;
    }

    Optional<Class> parametrizableClass = typeDescriptor.getErasuredType();
    parametrizableClass.ifPresent((clase) -> {
      JavaTypeDescriptor erasuredTypeDescriptor = JavaTypeDescriptor.createFor(clase);
      registerArguments(erasuredTypeDescriptor, typeArguments);

      Map<TypeVariable, Type> typeParameterValues = this.getTypeParameterValues(clase, typeArguments);
      Stream<Type> superTypes = erasuredTypeDescriptor.getGenericSupertypes();
      superTypes.forEach(superType ->{
        JavaTypeDescriptor superTypeDescriptor = JavaTypeDescriptor.createFor(superType);
        Type[] replacedArguments = superTypeDescriptor.getTypeArgumentsReplacingParametersWith(typeParameterValues);
        this.registerArguments(superTypeDescriptor, replacedArguments);
      });
    });
  }

  private Map<TypeVariable, Type> getTypeParameterValues(Class clase, Type[] typeArguments) {
    TypeVariable[] typeParameters = clase.getTypeParameters();
    if(typeArguments.length != typeParameters.length){
      throw new IllegalStateException("How does class["+clase+"] have parameters " + Arrays.toString(typeParameters) + " and mismatching args " + Arrays.toString(typeArguments));
    }
    Map<TypeVariable, Type> parameterReplacements = new LinkedHashMap<>();
    for (int i = 0; i < typeParameters.length; i++) {
      TypeVariable typeParameter = typeParameters[i];
      Type typeArgument = typeArguments[i];
      parameterReplacements.put(typeParameter, typeArgument);
    }
    return parameterReplacements;
  }

  public Stream<Type> getArgumentsFor(Type parametrizableClass) {
    Type[] actualArguments = argumentsByType.get(parametrizableClass);
    if (argumentsByType == null) {
      return Stream.empty();
    }
    return Arrays.stream(actualArguments);
  }
}
