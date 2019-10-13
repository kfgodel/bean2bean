package info.kfgodel.bean2bean.v3.dsl.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import info.kfgodel.bean2bean.v3.converters.collections.Collection2CollectionConverter;
import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.v3.dsl.impl.Dsl;
import info.kfgodel.bean2bean.v3.other.references.SupplierRef;
import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import info.kfgodel.reflect.references.TypeRef;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class verifies that a converter can have access to the context of the initial task inside a nested task
 *
 * Date: 25/03/19 - 00:52
 */
@RunWith(JavaSpecRunner.class)
public class TaskChainingTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a b2b dsl", () -> {
      test().dsl(Dsl::create);

      describe("when converters can delegate to each other", () -> {
        beforeEach(()->{
          // Converter for collections
          test().dsl().configure().useConverter(Collection2CollectionConverter.create());
          test().dsl().configure().scopingTo().accept(Nothing.class).andProduce(List.class)
            .useConverter(new SupplierRef<List>(ArrayList::new) {});
          // Converter for elements of the collection that have access to the collection
          test().dsl().configure().useConverter(this::converterThatAccessParentTask);
        });

        it("has no parent task if there's no nesting on the conversion",()->{
          String result = test().dsl().convert().from("element").to(String.class);
          assertThat(result).isEqualTo("element inside no container");
        });

        it("has a parent task to access the original task",()->{
          ArrayList<String> hostSource = Lists.newArrayList("element");
          List<String> list = test().dsl().convert().from(hostSource).to(new TypeRef<List<String>>() {});
          assertThat(list).contains("element inside ArrayList");
        });

        it("allows varying the conversion behavior based on the parent task",()->{
          Set<String> hostSource = Sets.newHashSet("element");
          List<String> result = test().dsl().convert().from(hostSource).to(new TypeRef<List<String>>() {});
          assertThat(result).contains("element inside HashSet");
        });
      });

    });

  }

  private Object converterThatAccessParentTask(Object source, Bean2beanTask task) {
    String nameOfContainerClass = task.getParentTask()
      .map(Bean2beanTask::getSource)
      .map(containerCollection -> containerCollection.getClass().getSimpleName())
      .orElse("no container");
    return source + " inside " + nameOfContainerClass;
  }
}
