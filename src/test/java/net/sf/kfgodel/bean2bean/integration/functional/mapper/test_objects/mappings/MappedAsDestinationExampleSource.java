package net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.mappings;

/**
 * Created by kfgodel on 31/08/14.
 */
public class MappedAsDestinationExampleSource {

    private String customGetter;

    private Integer customTransformer;

    private String customSetter;

    public String getCustomGetter() {
        return customGetter;
    }

    public void setCustomGetter(String customGetter) {
        this.customGetter = customGetter;
    }

    public Integer getCustomTransformer() {
        return customTransformer;
    }

    public void setCustomTransformer(Integer customTransformer) {
        this.customTransformer = customTransformer;
    }

    public String getCustomSetter() {
        return customSetter;
    }

    public void setCustomSetter(String customSetter) {
        this.customSetter = customSetter;
    }

    public static MappedAsDestinationExampleSource create() {
        MappedAsDestinationExampleSource source = new MappedAsDestinationExampleSource();
        source.customGetter = "Hello";
        source.customSetter = "Picnic";
        source.customTransformer = 1;
        return source;
    }


}
