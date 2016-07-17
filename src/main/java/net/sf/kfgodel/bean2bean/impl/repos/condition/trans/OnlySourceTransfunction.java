package net.sf.kfgodel.bean2bean.impl.repos.condition.trans;

import net.sf.kfgodel.bean2bean.impl.repos.condition.TransformationIntention;

import java.util.function.Function;

/**
 * This type represents a transfunction taht takes the source of teh intention and applies a basic transfunction to it
 * Created by kfgodel on 17/07/16.
 */
public class OnlySourceTransfunction<O> implements Function<TransformationIntention, O> {
  private Function<Object, O> basicTransfunction;

  @Override
  public O apply(TransformationIntention transformationIntention) {
    Object source = transformationIntention.source();
    O result = basicTransfunction.apply(source);
    return result;
  }

  public static<O> OnlySourceTransfunction<O> create(Function<?, O> basicTransfunction) {
    OnlySourceTransfunction<O> transformer = new OnlySourceTransfunction<>();
    transformer.basicTransfunction = (Function<Object, O>) basicTransfunction;
    return transformer;
  }

}
