package net.sf.kfgodel.bean2bean.impl.transformations.impl;

import net.sf.kfgodel.bean2bean.api.exceptions.Bean2beanException;

/**
 * This error is generated when an empty repository is used
 * Created by kfgodel on 05/02/15.
 */
public class EmptyRepositoryException extends Bean2beanException {
    
    public EmptyRepositoryException(String message) {
        super(message);
    }
}
