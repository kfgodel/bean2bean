package net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects;

/**
 * This type serves as a test object
 * Created by kfgodel on 16/08/14.
 */
public class AnnotatedTestDestination {

    private String destinationProperty;

    public String getDestinationProperty() {
        return destinationProperty;
    }

    public void setDestinationProperty(String destinationProperty) {
        this.destinationProperty = destinationProperty;
    }

    public static AnnotatedTestDestination create() {
        AnnotatedTestDestination destination = new AnnotatedTestDestination();
        return destination;
    }

}
