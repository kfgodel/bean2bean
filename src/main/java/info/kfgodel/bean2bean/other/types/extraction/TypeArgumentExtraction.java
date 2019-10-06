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
      .forEach(this::registerHierarchyReplacingArguments);
  }

  private void registerHierarchyReplacingArguments(JavaTypeDescriptor typeDescriptor) {
    Type[] typeArguments = typeDescriptor.getTypeArguments();
    registerArgumentsFor(typeDescriptor, typeArguments);
  }

  private void registerArgumentsFor(JavaTypeDescriptor typeDescriptor, Type[] typeArguments) {
    Type[] previouslyStored = storeArgumentsFor(typeDescriptor, typeArguments);
    if(previouslyStored != null){
      // No need to run the rest, the hierarchy was already registered
      return;
    }

    Optional<Class> erasuredType = typeDescriptor.getErasuredType();
    erasuredType
      .map(JavaTypeDescriptor::createFor)
      .ifPresent((erasuredDescriptor) -> {
        registerArgumentsFor(erasuredDescriptor, typeArguments);
        bubbleArgumentsUp(erasuredDescriptor, typeArguments);
      });
  }

  private void bubbleArgumentsUp(JavaTypeDescriptor typeDescriptor, Type[] actualArguments) {
    Map<TypeVariable, Type> typeVariableBindings = typeDescriptor.calculateTypeVariableBindingsFor(actualArguments);
    Stream<Type> superTypes = typeDescriptor.getGenericSupertypes();
    superTypes
      .map(JavaTypeDescriptor::createFor)
      .forEach(superTypeDescriptor -> registerArgumentsBindingTypeVariables(superTypeDescriptor, typeVariableBindings));
  }

  private Type[] storeArgumentsFor(JavaTypeDescriptor typeDescriptor, Type[] typeArguments) {
    Type aType = typeDescriptor.getType();
    return argumentsByType.putIfAbsent(aType, typeArguments);
  }

  private void registerArgumentsBindingTypeVariables(JavaTypeDescriptor superTypeDescriptor, Map<TypeVariable, Type> variableValues) {
    Type[] replacedArguments = superTypeDescriptor.getTypeArgumentsBindedWith(variableValues);
    this.registerArgumentsFor(superTypeDescriptor, replacedArguments);
  }

  public Stream<Type> getArgumentsFor(Type parametrizableClass) {
    Type[] actualArguments = argumentsByType.get(parametrizableClass);
    if (actualArguments == null) {
      return Stream.empty();
    }
    return Arrays.stream(actualArguments);
  }
}
