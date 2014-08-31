package net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects;

import net.sf.kfgodel.bean2bean.api.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.api.annotations.CopyTo;

/**
 * This type serves as a test object
 * Created by kfgodel on 16/08/14.
 */
public class AnnotatedTestDestination {

    @CopyFrom("sourceProperty")
    private String destinationProperty;

    private Boolean mappedFromSource;

    @CopyFrom(value="true", interpreter="groovy")
    private Boolean mappedFromDestination;

    public Boolean getMappedFromSource() {
        return mappedFromSource;
    }

    public void setMappedFromSource(Boolean mappedFromSource) {
        this.mappedFromSource = mappedFromSource;
    }

    public Boolean getMappedFromDestination() {
        return mappedFromDestination;
    }


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
