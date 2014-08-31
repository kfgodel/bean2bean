package net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects;

import net.sf.kfgodel.bean2bean.api.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.api.annotations.CopyTo;

/**
 * This type serves as a test object with annotations on the source
 * Created by kfgodel on 16/08/14.
 */
public class AnnotatedTestSource {

    @CopyTo("destinationProperty")
    private String sourceProperty;

    @CopyTo
    private Boolean mappedFromSource = true;

    public String getSourceProperty() {
        return sourceProperty;
    }

    public void setSourceProperty(String sourceProperty) {
        this.sourceProperty = sourceProperty;
    }


    public static AnnotatedTestSource create(String sourcePropertyValue) {
        AnnotatedTestSource source = new AnnotatedTestSource();
        source.sourceProperty = sourcePropertyValue;
        return source;
    }

}
