package net.sf.kfgodel.bean2bean.api.exceptions;

/**
 * This type represents an error on the normal operation of bean2bean
 * Created by kfgodel on 05/02/15.
 */
public class Bean2beanException extends RuntimeException {


    public Bean2beanException(String message) {
        super(message);
    }

    public Bean2beanException(String message, Throwable cause) {
        super(message, cause);
    }

    public Bean2beanException(Throwable cause) {
        super(cause);
    }
}
