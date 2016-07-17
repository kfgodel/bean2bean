package net.sf.kfgodel.bean2bean.impl.repos.condition.trans;

import net.sf.kfgodel.bean2bean.impl.repos.condition.TransformationIntention;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * This type represents a transfunction that only needs the source and destination objects as context to provide
 * the source converting transfunction
 * Created by kfgodel on 17/07/16.
 */
public class OnlySourceAndDestTransfunction<O> implements Function<TransformationIntention, O> {

  private BiFunction<Object, Object, Function<Object, O>> transfunctionProvider;

  @Override
  public O apply(TransformationIntention transformationIntention) {
    Object source = transformationIntention.source();
    Object destination = transformationIntention.destination();
    Function<Object, O> transfunction = transfunctionProvider.apply(source, destination);
    O result = transfunction.apply(source);
    return result;
  }

  public static<O> OnlySourceAndDestTransfunction<O> create(BiFunction<Object, Object, Function<Object, O>> transfunctionProvider) {
    OnlySourceAndDestTransfunction<O> transformer = new OnlySourceAndDestTransfunction<>();
    transformer.transfunctionProvider = transfunctionProvider;
    return transformer;
  }

}
