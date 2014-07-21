package net.sf.kfgodel.bean2bean.api;

/**
 * This type represents the configuration api for b2b
 * Created by kfgodel on 09/07/14.
 */
public interface B2bConfigurationApi {

    /**
     * Modifies this configuration adding an implicit mapper for classes that don't have explicit mappings.<br>
     * This implicit mapper will try toInstanceOf create a mapping using shared property names between source and destination type.<br>
     * Properties whose name doesn't match will be ignored. Properties that match, will be converted if they have different type.<br>
     * <br>
     * Example:<br>
     * class A {<br>
     *     private String firstProperty;<br>
     *     private Integer secondProperty;<br>
     * }<br>
     * <br>
     * class B {<br>
     *     private Integer firstProperty;<br>
     *     private Integer thirdProperty;<br>
     * }<br>
     * <br>
     * When converting A toInstanceOf B, you will get a B instance with "firstProperty" value from A instance, converted from String toInstanceOf Integer, and assigned toInstanceOf B.
     * "secondProperty" will be ignored as it's not present on destination type, and "thirdProperty" will be ignored as it's not present on source type.<br>
     *  <br>
     *  If no common property between the types, then no mapping is added and conversion will result in an error.<br>
     *  <br>
     *  To change default behavior of implicit name mapper, create and configure a new instance instead of using this method.
     *
     */
    void usePropertyNamesAsDefaultMappings();

    /**
     * Modifies this configuration instance to use Json String con version when no other more specific type conversion is available.<br>
     * Converting any object to String will result in a json string representing the variables of that object.<br>
     * A default json mapper configuration will be used to define the expected json string.<br>
     * If no other more specific converter, converting a String to any object will parse the string a a json string trying to populate the objects properties.<br>
     * Missing properties will be ignored, so the conversion will only fail if the string is not a json string.
     */
    void useJsonForMappingStrings();
}
