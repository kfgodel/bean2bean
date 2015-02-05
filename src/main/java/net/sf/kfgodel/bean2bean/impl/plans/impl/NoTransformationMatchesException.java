package net.sf.kfgodel.bean2bean.impl.plans.impl;

import net.sf.kfgodel.bean2bean.api.exceptions.Bean2beanException;

/**
 * This error is generated when an object lacks a transformation in a repo
 * Created by kfgodel on 05/02/15.
 */
public class NoTransformationMatchesException extends Bean2beanException {
    
    public NoTransformationMatchesException(String message) {
        super(message);
    }
}
