package net.sf.kfgodel.bean2bean.api.partials;

/**
 * This type represents  a partially defined operation over an instance and one of its fieldss
 * Created by kfgodel on 16/08/14.
 */
public interface ManipulatePropertyPartial<T> {
    /**
     * Returns the value of the property being manipulated
     * @param <R> The expected property value type
     * @return The value of the property
     */
    <R> R andGetItsValue();

    /**
     * Sets the value of the property being manipulated to given value
     * @param newValue The new value to be set on the property
     */
    void andSetItsValueTo(Object newValue);
}
