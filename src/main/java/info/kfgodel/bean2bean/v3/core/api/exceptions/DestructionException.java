package info.kfgodel.bean2bean.v3.core.api.exceptions;

/**
 * This class represents an error during the destruction of an instance
 * Date: 17/02/19 - 12:18
 */
public class DestructionException extends Bean2BeanException {

  private final Object destroyedObject;

  public DestructionException(String message, Object destroyedObject, Throwable cause) {
    super(message, cause);
    this.destroyedObject = destroyedObject;
  }

  public Object getDestroyedObject() {
    return destroyedObject;
  }
}
