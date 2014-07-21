package net.sf.kfgodel.bean2bean.integration.functional.converter;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.Variable;
import net.sf.kfgodel.bean2bean.api.B2bApi;
import net.sf.kfgodel.bean2bean.api.impl.B2bApiImpl;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.TypicalPerson;
import org.junit.runner.RunWith;

import static net.sf.kfgodel.bean2bean.assertions.B2bAssertions.assertThat;

/**
 * This type defines common test cases for bean2bean as an object converter<br>
 *
 *    In order to get the type of object I need
 *    As a bean2bean api user
 *    I want to be able to convert any type of object to any other type
 *
 * Created by kfgodel on 17/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class UniversalObjectConverterIT extends JavaSpec {

    @Override
    public void define() {

        Variable<B2bApi> b2b = Variable.create();


        beforeEach(()->{
            b2b.set(B2bApiImpl.create());
            b2b.get().configuration().useJsonForMappingStrings();
        });

        /**
         * Given A default configured bean2bean type converter
         * And a configured mapping from any object to Json
         * When I convert the domain object to String
         * Then I should obtain a Json representation of the object
         */
        it("can convert a typical domain object to Json String", ()->{
            //Given
            TypicalPerson domainObject = TypicalPerson.createWithTestState();

            //When
            String jsonString = b2b.get().convert().from(domainObject).toInstanceOf(String.class);

            //Then
            assertThat(jsonString).isEqualTo("{}");
        });

        /**
         * Given A default configured bean2bean type converter
         * And a configured mapping from Json to any object
         * When I convert the String to a domain object
         * Then I should obtain an Object representation of the Json
         */
        it("can convert a Json String to a typical domain object", ()->{
            //Given
            String jsonString = "{}";

            //When
            TypicalPerson domainObject = b2b.get().convert().from(jsonString).toInstanceOf(TypicalPerson.class);

            //Then
            assertThat(domainObject).isNotNull();
        });

    }
}
