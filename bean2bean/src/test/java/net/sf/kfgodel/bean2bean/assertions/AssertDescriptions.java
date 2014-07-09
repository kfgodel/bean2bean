package net.sf.kfgodel.bean2bean.assertions;

import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.PersonDto;
import org.assertj.core.api.WritableAssertionInfo;

/**
 * This type helps on describing custom assertions
 * Created by kfgodel on 09/07/14.
 */
public class AssertDescriptions {

    public static String getNestedDescription(WritableAssertionInfo info, Object actual, String propertyName) {
        String previousDescription = info.descriptionText();
        if(previousDescription == null){
            return propertyName;
        }
        return previousDescription + " " + propertyName;
    }

}
