package info.kfgodel.bean2bean.v3.other.references;

import info.kfgodel.reflect.types.extraction.TypeArgumentExtractor;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * This class serves as a way to declare function lambdas while retaining their type argument on runtime.<br>
 * By creating subclasses of this type you can get the {@link java.lang.reflect.Type} arguments used to
 * parameterize the function.
 * <p>
 * Date: 13/02/19 - 18:57
 */
public abstract class FunctionRef<I, O> {

  private final Function<I, O> function;
  private Type[] actualTypeArguments;

  public FunctionRef(Function<? super I, ? extends O> function) {
    this.function = (Function<I, O>) function;
  }

  public Type[] getActualTypeArguments() {
    if (actualTypeArguments == null) {
      actualTypeArguments = calculateTypeArguments();
    }
    return actualTypeArguments;
  }

  private Type[] calculateTypeArguments() {
    Type[] arguments = TypeArgumentExtractor.create()
      .getArgumentsUsedFor(FunctionRef.class, getClass())
      .toArray(Type[]::new);

    if(arguments.length != 2){
      throw new IllegalStateException(FunctionRef.class.getSimpleName() + " should be parameterized when extended");
    }
    return arguments;
  }

  public Function<I, O> getFunction() {
    return function;
  }

  public Type getInputType() {
    return getActualTypeArguments()[0];
  }

  public Type getOutputType() {
    return getActualTypeArguments()[1];
  }

}
