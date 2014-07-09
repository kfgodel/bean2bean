package net.sf.kfgodel.bean2bean.api;

/**
 * This type represents the api interface for bean2bean as converter
 * Created by kfgodel on 09/07/14.
 */
public interface B2bConverterApi {

    /**
     * Starts a conversion definition by setting the source value.<br>
     * A subsequent call to method to() on returned object will complete the definition and make the conversion.
     *
     * @param sourceValue Value to convert to another kind
     * @return A partial definition to be completed
     */
    SourceDefinedConversionApi from(Object sourceValue);
}
