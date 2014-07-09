package net.sf.kfgodel.bean2bean.api;

/**
 * This type representes a request for conversion that has defined the source value, but lacks the destination type
 * Created by kfgodel on 09/07/14.
 */
public interface SourceDefinedConversionApi {

    /**
     * Completes the conversion definition by setting the destination value, and makes the conversion
     * returning the converted value.<br>
     *
     * @param destinationValue The value that defines the expected result for the conversion.<br> Normally a type instance
     * @param <T> The type of expected result to be inferred
     * @return The conversion result
     */
    <T> T to(Object destinationValue);
}
