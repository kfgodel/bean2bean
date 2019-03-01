package info.kfgodel.bean2bean.converters;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.Lists;
import info.kfgodel.bean2bean.core.api.exceptions.CreationException;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import info.kfgodel.bean2bean.other.references.TypeRef;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 28/02/19 - 19:48
 */
@RunWith(JavaSpecRunner.class)
public class Collection2CollectionConverterTest extends JavaSpec<ConverterTestContext> {
  @Override
  public void define() {
    describe("a collection converter registered with b2b", () -> {
      beforeEach(()->{
        test().dsl().configure().usingConverter(Collection2CollectionConverter.create());
      });

      context().dsl(Dsl::create);

      describe("by itself", () -> {
        itThrows(CreationException.class, "if no creation converter is registered",()->{
          test().dsl().convert().from(listWith12And2()).to(setOfStrings());
        }, e ->{
          assertThat(e).hasMessage("Creation from null to java.util.Set<java.lang.String> failed: No converter found from null{javax.lang.model.type.NullType} to {java.util.Set<java.lang.String>}");
        });
      });


    });

  }

  private TypeRef<Set<String>> setOfStrings() {
    return new TypeRef<Set<String>>() {};
  }

  private List<Integer> listWith12And2() {
    return Lists.newArrayList(1, 2, 2);
  }
}