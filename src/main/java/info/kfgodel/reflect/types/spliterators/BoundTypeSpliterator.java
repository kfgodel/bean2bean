package info.kfgodel.reflect.types.spliterators;

import info.kfgodel.reflect.types.binding.BoundType;
import info.kfgodel.reflect.types.binding.DefaultBoundType;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * This spliterator allows traversing all the supertypes of a bound type without duplicates
 * Date: 29/9/19 - 17:07
 */
public class BoundTypeSpliterator implements Spliterator<BoundType> {
  
  private HashSet<String> traversedTypeNames;
  private Queue<BoundType> pendingTypes;

  @Override
  public boolean tryAdvance(Consumer<? super BoundType> action) {
    while(!pendingTypes.isEmpty()){
      BoundType nextType = pendingTypes.remove();
      markAsTraversed(nextType); // We use name bc parameterized type don't implement equals correctly
      action.accept(nextType);
      enqueueSupertypesOf(nextType);
      return true;
    }
    if(!wasTraversed(Object.class)){
      // This exception ensures Object is last and present on interface hierarchies
      final DefaultBoundType objectType = DefaultBoundType.create(Object.class);
      markAsTraversed(objectType);
      action.accept(objectType);
      return true;
    }
    return false;
  }

  private void enqueueSupertypesOf(BoundType nextType) {
    nextType.getDirectSupertypes()
      .forEach(this::enqueue);
  }

  private void markAsTraversed(BoundType nextType) {
    traversedTypeNames.add(nextType.getRawType().getTypeName());
  }

  @Override
  public Spliterator<BoundType> trySplit() {
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

  public static BoundTypeSpliterator create(BoundType firstType) {
    BoundTypeSpliterator spliterator = new BoundTypeSpliterator();
    spliterator.traversedTypeNames = new HashSet<>();
    spliterator.pendingTypes = new LinkedList<>();
    spliterator.enqueue(firstType);
    return spliterator;
  }

  private void enqueue(BoundType aType) {
    if(aType == null || // We deal with null here so we don't have to do it for every jdk method
      wasTraversed(aType.getRawType()) || // No need to traverse it again
      isPending(aType) || // It's already on the waiting list
      Object.class.equals(aType.getRawType()) // Object is a type we add to the end, we ignore it here
    ){
      return; // Don't add it
    }
    this.pendingTypes.add(aType);
  }

  private boolean isPending(BoundType aType) {
    final String aTypeName = aType.getRawType().getTypeName();
    return pendingTypes.stream()
      .map(BoundType::getRawType)
      .map(Type::getTypeName)
      .anyMatch(aTypeName::equals);
  }

  private boolean wasTraversed(Type aType) {
    return traversedTypeNames.contains(aType.getTypeName());
  }

}
