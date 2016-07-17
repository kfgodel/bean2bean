package net.sf.kfgodel.bean2bean.impl.repos.condition;

import com.google.common.base.MoreObjects;

/**
 * Created by kfgodel on 17/07/16.
 */
public class TransformationIntentionImpl implements TransformationIntention {

  private Object source;
  public static final String source_FIELD = "source";

  private Object destination;
  public static final String destination_FIELD = "destination";


  @Override
  public Object source() {
    return source;
  }

  @Override
  public Object destination() {
    return destination;
  }

  public static TransformationIntentionImpl create(Object source, Object destination) {
    TransformationIntentionImpl intention = new TransformationIntentionImpl();
    intention.source = source;
    intention.destination = destination;
    return intention;
  }


  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add(source_FIELD, source)
      .add(destination_FIELD, destination)
      .toString();
  }
}
