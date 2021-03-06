package info.kfgodel.reflect.types.spliterators;

import info.kfgodel.reflect.types.descriptors.JavaTypeDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This class represents the spliterator that traverses all the super types for a given type (including the type itself)
 * Date: 17/02/19 - 21:16
 */
public class SupertypeSpliterator implements Spliterator<Type> {
  public static Logger LOG = LoggerFactory.getLogger(SupertypeSpliterator.class);

  private Set<String> traversedTypeNames;
  private Queue<Type> pendingTypes;

  public static Stream<Type> createAsStream(Type startingType) {
    return StreamSupport.stream(create(startingType), false);
  }

  public static SupertypeSpliterator create(Type startingType) {
    SupertypeSpliterator spliterator = new SupertypeSpliterator();
    spliterator.traversedTypeNames = new HashSet<>();
    spliterator.pendingTypes = new LinkedList<>();
    spliterator.enqueue(startingType);
    return spliterator;
  }

  @Override
  public boolean tryAdvance(Consumer<? super Type> action) {
    while(!pendingTypes.isEmpty()){
      Type nextType = pendingTypes.remove();
      markAsTraversed(nextType); // We use name bc parameterized type don't implement equals correctly
      action.accept(nextType);
      enqueueSupertypesOf(nextType);
      return true;
    }
    if(!wasTraversed(Object.class)){
      // This exception ensures Object is last and present on interface hierarchies
      markAsTraversed(Object.class);
      action.accept(Object.class);
      return true;
    }
    return false;
  }

  private boolean markAsTraversed(Type nextType) {
    return traversedTypeNames.add(nextType.getTypeName());
  }

  private boolean wasTraversed(Type type) {
    return traversedTypeNames.contains(type.getTypeName());
  }

  @Override
  public Spliterator<Type> trySplit() {
    // We cannot split
    return null;
  }

  @Override
  public long estimateSize() {
    //If we don't know the size, contract is to return max value
    return Long.MAX_VALUE;
  }

  @Override
  public int characteristics() {
    return Spliterator.DISTINCT & Spliterator.IMMUTABLE & Spliterator.NONNULL & Spliterator.ORDERED;
  }

  private void enqueueSupertypesOf(Type visitedType) {
    if(visitedType instanceof Class){
      enqueueSupertypesOf((Class)visitedType);
    }else if(visitedType instanceof ParameterizedType){
      enqueueSupertypesOf((ParameterizedType)visitedType);
    }else if(visitedType instanceof TypeVariable){
      enqueueSupertypesOf((TypeVariable)visitedType);
    }else if(visitedType instanceof WildcardType){
      enqueueSupertypesOf((WildcardType)visitedType);
    }else if(visitedType instanceof GenericArrayType){
      enqueueSupertypesOf((GenericArrayType)visitedType);
    }else{
      // For the rest of types we don't have a way to explore supertypes (yet)
      LOG.warn("No known methods for obtaining the supertypes of: {}", visitedType);
    }
  }

  private void enqueueSupertypesOf(ParameterizedType visitedType){
    // No way to explore parameterized supertypes (yet)
    this.enqueue(visitedType.getRawType());
  }

  private void enqueueSupertypesOf(TypeVariable visitedType){
    Arrays.stream(visitedType.getBounds())
      .forEach(this::enqueue);
  }
  private void enqueueSupertypesOf(WildcardType visitedType){
    Arrays.stream(visitedType.getUpperBounds())
      .forEach(this::enqueue);

  }
  private void enqueueSupertypesOf(GenericArrayType visitedType){
    Optional<Class> assignableClass = JavaTypeDescriptor.createFor(visitedType)
      .getAssignableClass();
    assignableClass.ifPresent(this::enqueue);
  }

  private void enqueueSupertypesOf(Class visitedClass){
    // Generified types
    enqueue(visitedClass.getGenericSuperclass());
    Arrays.stream(visitedClass.getGenericInterfaces())
      .forEach(this::enqueue);
    // Raw types
    enqueue(visitedClass.getSuperclass());
    Arrays.stream(visitedClass.getInterfaces())
      .forEach(this::enqueue);
  }

  private void enqueue(Type pendingType){
    if(pendingType == null || // We deal with null here so we don't have to do it for every jdk method
      wasTraversed(pendingType) || // No need to traverse it again
      isPending(pendingType) || // It's already on the waiting list
      Object.class.equals(pendingType) // Object is a type we add to the end, we ignore it here
    ){
      return; // Don't add it
    }
    this.pendingTypes.add(pendingType);
  }

  private boolean isPending(Type aType) {
    // We need to look for name bc parameterized types don't implement equal correctly
    final String aTypeName = aType.getTypeName();
    return this.pendingTypes.stream()
      .map(Type::getTypeName)
      .anyMatch(pendingTypeName -> pendingTypeName.equals(aTypeName));
  }
}
