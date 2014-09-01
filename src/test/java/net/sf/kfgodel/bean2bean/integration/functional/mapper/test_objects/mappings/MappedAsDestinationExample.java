package net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.mappings;

import net.sf.kfgodel.bean2bean.api.annotations.B2bExpression;
import net.sf.kfgodel.bean2bean.api.annotations.MappedAsDestination;

/**
 * This type serves as an example of using MappedAsDestination annotation
 * Created by kfgodel on 31/08/14.
 */
public class MappedAsDestinationExample {

    @MappedAsDestination(from=@B2bExpression(value="true", interpreter = "groovy"))
    private String customGetter;

    @MappedAsDestination(transform=@B2bExpression(value="sourceValue.toString()", interpreter = "groovy"))
    private String customTransformer;

    @MappedAsDestination(to=@B2bExpression(value="destinationObject.derivatedProperty = destinationValue", interpreter = "groovy"))
    private Void customSetter;

    private String derivatedProperty;

    public void setCustomSetter(Void customSetter) {
        this.customSetter = customSetter;
    }

    public String getDerivatedProperty() {
        return derivatedProperty;
    }

    public void setDerivatedProperty(String derivatedProperty) {
        this.derivatedProperty = derivatedProperty;
    }

    public String getCustomGetter() {
        return customGetter;
    }

    public void setCustomGetter(String customGetter) {
        this.customGetter = customGetter;
    }

    public String getCustomTransformer() {
        return customTransformer;
    }

    public void setCustomTransformer(String customTransformer) {
        this.customTransformer = customTransformer;
    }
}
