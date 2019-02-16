package info.kfgodel.bean2bean.other;

import java.lang.reflect.Type;
import java.util.function.BiFunction;

/**
 * This class serves as a way to declare bifunction lambdas without losing
 * its type parameters. Subclasses of this type allows you to retain the
 * type arguments erased by the compiler
 * Date: 16/02/19 - 20:01
 */
public abstract class BiFunctionRef<I1,I2,O> {

  private BiFunction<I1,I2,O> biFunction;
  private Type[] actualTypeArguments;

  public Type[] getActualTypeArguments() {
    if (actualTypeArguments == null) {
      actualTypeArguments = TypeRef.getActualTypeArgumentsFrom(getClass(), BiFunctionRef.class);
    }
    return actualTypeArguments;
  }


  public BiFunctionRef(BiFunction<I1, I2, O> biFunction) {
    this.biFunction = biFunction;
  }

  public Type getFirstInputType(){
    return getActualTypeArguments()[0];
  }

  public Type getSecondInputType(){
    return getActualTypeArguments()[1];
  }

  public Type getOutputType(){
    return getActualTypeArguments()[2];
  }

  public BiFunction<I1, I2, O> getBiFunction() {
    return biFunction;
  }
}
