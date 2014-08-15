package net.sf.kfgodel.bean2bean.api;

import net.sf.kfgodel.bean2bean.api.partials.ConvertFromPartial;

/**
 * This type represents the api interface for bean2bean as converter
 * Created by kfgodel on 09/07/14.
 */
public interface B2bConverterApi {

    /**
     * Starts a conversion definition by setting the source value.<br>
     * A subsequent call toInstanceOf method toInstanceOf() on returned object will complete the definition and make the conversion.
     *
     * @param sourceValue Value toInstanceOf convert toInstanceOf another kind
     * @return A partial definition toInstanceOf be completed
     */
    ConvertFromPartial from(Object sourceValue);
}
