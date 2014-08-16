package net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects;

/**
 * this type serves as a test object
 * Created by kfgodel on 16/08/14.
 */
public class UnannotatedTestDestination {

    private String destinationProperty;

    private Boolean mappedFromSource;
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

    public void setMappedFromDestination(Boolean mappedFromDestination) {
        this.mappedFromDestination = mappedFromDestination;
    }

    public String getDestinationProperty() {
        return destinationProperty;
    }

    public void setDestinationProperty(String destinationProperty) {
        this.destinationProperty = destinationProperty;
    }


    public static UnannotatedTestDestination create() {
        UnannotatedTestDestination destination = new UnannotatedTestDestination();
        return destination;
    }

}
