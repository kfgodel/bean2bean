package net.sf.kfgodel.bean2bean.api.partials;

/**
 * This type represents a partial definition of a mapping with the source type defined
 * Created by kfgodel on 17/08/14.
 */
public interface MapFromPartial<S> {
    /**
     * Defines the destination type of the mapping with a class instance
     * @param destinationType the instance that defines the destination type
     * @param <D> The destination type
     * @return The partial mapping defined
     */
    <D> MapFromToPartial<S,D> toInstanceOf(Class<D> destinationType);
}
