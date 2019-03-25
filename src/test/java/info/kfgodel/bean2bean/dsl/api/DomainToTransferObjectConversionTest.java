package info.kfgodel.bean2bean.dsl.api;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.dsl.api.example.PersonTo;
import info.kfgodel.bean2bean.dsl.api.example.TestAddress;
import info.kfgodel.bean2bean.dsl.api.example.TestAddress2AddressToConverter;
import info.kfgodel.bean2bean.dsl.api.example.TestPerson;
import info.kfgodel.bean2bean.dsl.api.example.TestPerson2PersonToConverter;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import org.junit.runner.RunWith;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class demonstrates how to convert from an example domain object to a TO.<br>
 * Different approaches are used
 * Date: 24/03/19 - 20:32
 */
@RunWith(JavaSpecRunner.class)
public class DomainToTransferObjectConversionTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a b2b dsl", () -> {
      test().dsl(Dsl::createWitDefaultConverters);

      describe("given a domain object", () -> {
        test().person(() ->
          TestPerson.create("John Doe", LocalDate.of(1985, 10, 11))
            .with(TestAddress.create("2 pines st.", 2, "IL", "US"))
            .with(TestAddress.create("10 pines st.", 10, "CABA", "AR"))
        );

        describe("when no converter is registered", () -> {
          itThrows(ConversionException.class, "if converted to a TO", () -> {
            test().dsl().convert().from(test().person()).to(PersonTo.class);
          }, e -> {
            assertThat(e).hasMessage("No converter found from TestPerson{name='John Doe'} ∈ {info.kfgodel.bean2bean.dsl.api.example.TestPerson} to {info.kfgodel.bean2bean.dsl.api.example.PersonTo}");
          });
        });

        describe("when manually tailored converters are registered", () -> {
          beforeEach(()->{
            test().dsl().configure().useConverter(TestPerson2PersonToConverter.create());
            test().dsl().configure().useConverter(TestAddress2AddressToConverter.create());
          });

          it("can convert the domain to a TO",()->{
            PersonTo result = test().dsl().convert().from(test().person()).to(PersonTo.class);
            assertThat(result)
              .hasFieldOrPropertyWithValue("name", "John Doe")
              .hasFieldOrPropertyWithValue("birthday", "1985-10-11");
            assertThat(result.getAddresses()).hasSize(2);
            assertThat(result.getAddresses().get(0))
              .hasFieldOrPropertyWithValue("street", "2 pines st.")
              .hasFieldOrPropertyWithValue("number", 2)
              .hasFieldOrPropertyWithValue("state", "IL")
              .hasFieldOrPropertyWithValue("country", "US");
            assertThat(result.getAddresses().get(1))
              .hasFieldOrPropertyWithValue("street", "10 pines st.")
              .hasFieldOrPropertyWithValue("number", 10)
              .hasFieldOrPropertyWithValue("state", "CABA")
              .hasFieldOrPropertyWithValue("country", "AR");
          });
        });


      });


    });

  }
}