package info.kfgodel.bean2bean.core.impl.conversion;

import info.kfgodel.bean2bean.core.api.Bean2bean;
import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.core.impl.descriptor.ObjectDescriptor;
import info.kfgodel.bean2bean.core.impl.nesting.NestedBean2bean;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.dsl.impl.Dsl;

import java.lang.reflect.Type;
import java.util.Optional;

/**
 * This class represents the task that {@link info.kfgodel.bean2bean.core.api.Bean2bean} can process
 * to convert an object from one domain to another domain (possibly generating a new object)
 *
 * Date: 12/02/19 - 00:06
 */
public class ObjectConversion implements Bean2beanTask {

  private Object source;
  private Type targetType;
  private DomainVector conversionVector;
  private Bean2bean core;
  private Optional<Bean2beanTask> parentTask;

  public static ObjectConversion create(Object source, Type targetType, DomainVector conversionVector, Bean2bean core) {
    ObjectConversion conversion = new ObjectConversion();
    conversion.source = source;
    conversion.conversionVector = conversionVector;
    conversion.core = core;
    conversion.targetType = targetType;
    conversion.parentTask = Optional.empty();
    return conversion;
  }

  @Override
  public DomainVector getConversionVector(){
    return conversionVector;
  }

  @Override
  public Object getSource() {
    return source;
  }

  @Override
  public B2bDsl getDsl() {
    Bean2bean realB2b = this.core;
    if(core instanceof NestedBean2bean){
      // Avoid nesting nested cores so errors are properly reported
      realB2b = ((NestedBean2bean)this.core).getRealB2b();
    }
    NestedBean2bean nestedCore = NestedBean2bean.create(realB2b, this);
    return Dsl.createFor(nestedCore);
  }

  @Override
  public String describeSource() {
    ObjectDescriptor descriptor = ObjectDescriptor.create();
    return descriptor.describeSource(this.getSource(), this.getConversionVector().getSource());
  }

  @Override
  public String describeTarget() {
    ObjectDescriptor descriptor = ObjectDescriptor.create();
    return descriptor.describeTarget(this.getTargetType(), this.getConversionVector().getTarget());
  }

  @Override
  public Optional<Bean2beanTask> getParentTask() {
    return parentTask;
  }

  @Override
  public void linkToParent(Bean2beanTask originalTask) {
    this.parentTask = Optional.of(originalTask);
  }

  @Override
  public Type getTargetType() {
    return targetType;
  }

  @Override
  public String toString() {
    return source + " -> " + targetType + getParentTask()
      .map(parent -> " part of (" + parent + ")")
      .orElse("");
  }

}
