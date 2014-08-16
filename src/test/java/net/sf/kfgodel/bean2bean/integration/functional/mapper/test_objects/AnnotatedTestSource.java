package net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects;

/**
 * This type serves as a test object with annotations on the source
 * Created by kfgodel on 16/08/14.
 */
public class AnnotatedTestSource {

    private String sourceProperty;

    public String getSourceProperty() {
        return sourceProperty;
    }

    public void setSourceProperty(String sourceProperty) {
        this.sourceProperty = sourceProperty;
    }


    public static AnnotatedTestSource create() {
        AnnotatedTestSource source = new AnnotatedTestSource();
        return source;
    }

}
