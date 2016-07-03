package net.sf.kfgodel.bean2bean.api;

/**
 * This interface represents the global api for bean2bean
 * Created by kfgodel on 09/07/14.
 */
public interface B2bApi {

    /**
     * Allows access to bean2bean runtime configuration.<br>
     *     Modifications on this object take immediate action
     * @return The b2b configuration
     */
    B2bConfigurationApi configuration();

    /**
     * Allows access to b2b as a converter object, offering methods specific for conversion
     * @return The converter api
     */
    B2bConverterApi convert();

    /**
     * Allows access to b2b as a bean manipulator offering methods for working with objects
     * @return The bean manipulation api
     */
    B2bManipulatorApi manipulate();

    /**
     * Allows access to b2b as an object mapper offering methods for transfering state between objects
     * @return The mapper api for defining type mappings
     */
    B2bMapperApi map();
}
