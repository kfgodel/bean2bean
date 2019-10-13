package info.kfgodel.bean2bean.v3.dsl.api;

import info.kfgodel.bean2bean.v3.converters.mapping.MappingConverter;
import info.kfgodel.bean2bean.v3.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.v3.dsl.api.example.AddressTo;
import info.kfgodel.bean2bean.v3.dsl.api.example.PersonTo;
import info.kfgodel.bean2bean.v3.dsl.api.example.TestAddress;
import info.kfgodel.bean2bean.v3.dsl.api.example.TestAddress2AddressToConverter;
import info.kfgodel.bean2bean.v3.dsl.api.example.TestPerson;
import info.kfgodel.bean2bean.v3.dsl.api.example.TestPerson2PersonToConverter;
import info.kfgodel.bean2bean.v3.dsl.impl.Dsl;
import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import info.kfgodel.reflect.references.TypeRef;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.List;

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
            assertThat(e).hasMessage("No converter found from TestPerson{name='John Doe'} ∈ {info.kfgodel.bean2bean.v3.dsl.api.example.TestPerson} to {info.kfgodel.bean2bean.v3.dsl.api.example.PersonTo}");
          });
        });

        describe("when custom made converters are registered", () -> {
          beforeEach(()->{
            test().dsl().configure().useConverter(TestPerson2PersonToConverter.create());
            test().dsl().configure().useConverter(TestAddress2AddressToConverter.create());
          });

          it("can convert the domain to a TO", this::convertAndVerifyResult);
        });

        describe("when mapping converters are registered", () -> {
          beforeEach(()->{
            test().dsl().configure().scopingTo().accept(TestPerson.class).andProduce(PersonTo.class)
              .useConverter(this.createPersonMappingConverter());
            test().dsl().configure().scopingTo().accept(TestAddress.class).andProduce(AddressTo.class)
              .useConverter(this.createAddressMappingConverter());
          });

          it("can convert the domain to a TO", this::convertAndVerifyResult);
        });

        describe("when using the dsl to define mapping converters", () -> {
          beforeEach(()->{

            test().dsl().configure().scopingTo().accept(TestPerson.class).andProduce(PersonTo.class)
              .useMapper(aMapper -> aMapper
                .getFrom(TestPerson::getName).setInto(PersonTo::setName)
                .getFrom(TestPerson::getBirthday).convertTo(String.class).setInto(PersonTo::setBirthday)
                .getFrom(TestPerson::getAddresses).convertTo(new TypeRef<List<AddressTo>>() {}).setInto(PersonTo::setAddresses)
              );

            test().dsl().configure().scopingTo().accept(TestAddress.class).andProduce(AddressTo.class)
              .useMapper(aMapper -> aMapper
                .getFrom(TestAddress::getCountry).setInto(AddressTo::setCountry)
                .getFrom(TestAddress::getState).setInto(AddressTo::setState)
                .getFrom(TestAddress::getStreetName).setInto(AddressTo::setStreet)
                .getFrom(TestAddress::getStreetNumber).setInto(AddressTo::setNumber)
              );
          });

          it("can convert the domain to a TO", this::convertAndVerifyResult);
        });


      });

    });
  }

  private MappingConverter<TestPerson, PersonTo> createPersonMappingConverter() {
    return MappingConverter.<TestPerson, PersonTo>create()
      .withMapping(TestPerson::getName, PersonTo::setName)
      .withMapping(TestPerson::getBirthday, String.class, PersonTo::setBirthday)
      .withMapping(TestPerson::getAddresses, new TypeRef<List<AddressTo>>() {}, PersonTo::setAddresses);
  }

  private MappingConverter<TestAddress, AddressTo> createAddressMappingConverter() {
    return MappingConverter.<TestAddress, AddressTo>create()
      .withMapping(TestAddress::getCountry, AddressTo::setCountry)
      .withMapping(TestAddress::getState, AddressTo::setState)
      .withMapping(TestAddress::getStreetName, AddressTo::setStreet)
      .withMapping(TestAddress::getStreetNumber, AddressTo::setNumber);
  }

  private void convertAndVerifyResult() {
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
  }
}