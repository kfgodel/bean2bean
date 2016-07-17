package net.sf.kfgodel.bean2bean.unit.repo;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.nary.api.optionals.Optional;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import net.sf.kfgodel.bean2bean.impl.repos.primitive.PrimitiveTransfunctionRepoImpl;
import net.sf.kfgodel.bean2bean.impl.repos.primitive.partials.SourceDefinedPrimitiveTransfunctionDefinition;
import org.junit.runner.RunWith;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Esta clase verifica el comportamiento general del conversor de referencia de tipos primitivos
 * Created by kfgodel on 08/07/16.
 */
@RunWith(JavaSpecRunner.class)
public class PrimitiveTransfunctionRepoTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a primitive transfunciton repo", () -> {
      context().primitiveRepo(PrimitiveTransfunctionRepoImpl::create);
      given(()->{});

      describe("gives access to transfunction for converting primitive types", () -> {
        then(() -> {
          assertThat(context().transfunction()).isNotNull();
        });

        it("using a fluent interface", () -> {
          when(() -> {
            context().transfunction(() -> context().primitiveRepo().fromString().toLong());
          });
          executeAsGivenWhenThenTest();
        });

        it("using a class variable for the destination type", () -> {
          when(() -> {
            context().transfunction(() -> context().primitiveRepo().fromString().toType(Long.class).get());
          });
          executeAsGivenWhenThenTest();
        });
        
        it("using a class variable for the source type",()->{
          when(()-> {
            context().transfunction(()-> context().primitiveRepo().fromType(String.class).get().toLong());
          });
          executeAsGivenWhenThenTest();
        });   
        
        it("using class variables for source and destination type",()->{
          when(()-> {
            context().transfunction(()-> context().primitiveRepo().fromType(String.class).get().toType(Long.class).get());
          });
          executeAsGivenWhenThenTest();
        });   
        
        it("returns an empty optional if source type is not a primitive",()->{
          Optional<SourceDefinedPrimitiveTransfunctionDefinition<Object>> partial = context().primitiveRepo().fromType(Object.class);
          assertThat(partial.isAbsent()).isTrue();
        });

        it("returns an empty optional if destination type is not a primitive",()->{
          Optional<Function<String, Object>> transfunction = context().primitiveRepo().fromString().toType(Object.class);
          assertThat(transfunction.isAbsent()).isTrue();
        });

      });

    });

  }
}
