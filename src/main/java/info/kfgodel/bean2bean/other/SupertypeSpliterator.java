package info.kfgodel.bean2bean.other;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
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

  private Set<Type> traversedTypes;
  private Queue<Type> pendingTypes;

  public static Stream<Type> createAsStream(Type startingType) {
    return StreamSupport.stream(create(startingType), false);
  }

  public static SupertypeSpliterator create(Type startingType) {
    SupertypeSpliterator spliterator = new SupertypeSpliterator();
    spliterator.traversedTypes = new HashSet<>();
    spliterator.pendingTypes = new LinkedList<>();
    spliterator.enqueue(startingType);
    return spliterator;
  }

  @Override
  public boolean tryAdvance(Consumer<? super Type> action) {
    while(!pendingTypes.isEmpty()){
      Type nextType = pendingTypes.remove();
      traversedTypes.add(nextType);
      action.accept(nextType);
      enqueueSupertypesOf(nextType);
      return true;
    }
    if(!traversedTypes.contains(Object.class)){
      // This exception ensures Object is last and present on interface hierarchies
      traversedTypes.add(Object.class);
      action.accept(Object.class);
      return true;
    }
    return false;
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
    }else{
      // For the rest of types we don't have a way to explore supertypes (yet)
    }
  }

  private void enqueueSupertypesOf(ParameterizedType visitedType){
    // No way to explore parameterized supertypes (yet)
    this.enqueue(visitedType.getRawType());
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
      Object.class.equals(pendingType) || // Object is a type we add to the end, no matter what
      this.traversedTypes.contains(pendingType) || // No need to traverse it again
      this.pendingTypes.contains(pendingType) // It's already on the waiting list
    ){
      return; // Don't add it
    }
    this.pendingTypes.add(pendingType);
  }
}
