package info.kfgodel.bean2bean.other;

import java.lang.reflect.Type;
import java.util.function.Consumer;

/**
 * This class serves as a way to declare consumer lambdas whithout losing its
 * type metadata when compiler erasure takes place.<br>
 *   Subclasses of this type allows you to access the lambda type parameter
 * Date: 17/02/19 - 13:25
 */
public class ConsumerRef<I> {

  private final Consumer<I> consumer;

  public ConsumerRef(Consumer<I> consumer) {
    this.consumer = consumer;
  }

  public Consumer<I> getConsumer() {
    return consumer;
  }

  public Type getInputType(){
    return TypeArgumentExtractor.create()
      .getArgumentUsedFor(ConsumerRef.class, getClass())
      .orElseThrow(()-> new IllegalStateException(ConsumerRef.class.getSimpleName() + " should be parameterized when extended"));
  }
}
