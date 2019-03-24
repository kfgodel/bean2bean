package info.kfgodel.bean2bean.converters;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import info.kfgodel.bean2bean.other.references.TypeRef;
import org.junit.runner.RunWith;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class tests the conversion interaction between default converters when are all added to the config
 * Date: 24/03/19 - 14:22
 */
@RunWith(JavaSpecRunner.class)
public class DefaultConvertersTest extends JavaSpec<ConverterTestContext> {
  @Override
  public void define() {
    describe("a b2b dsl with default converters", () -> {
      beforeEach(() -> {
        test().dsl().configure().usingDefaultConverters();
      });
      test().dsl(Dsl::create);

      it("can convert and object to itself", () -> {
        String source = "Hello World!";
        String result = test().dsl().convert().from(source).to(String.class);
        assertThat(result).isSameAs(source);
      });

      describe("for optionals", () -> {
        it("can extract the element inside an optional", () -> {
          String text = "Hello World!";
          String result = test().dsl().convert().from(Optional.of(text)).to(String.class);
          assertThat(result).isSameAs(text);
        });

        it("can wrap an object into an optional", () -> {
          String text = "Hello World!";
          Optional<String> result = test().dsl().convert().from(text).to(new TypeRef<Optional<String>>() {
          });
          assertThat(result.get()).isSameAs(text);
        });
      });

      describe("for collections", () -> {
        it("can convert from one type to other including elements", () -> {
          List<String> source = Lists.newArrayList("1", "2", "3");
          Set<Integer> result = test().dsl().convert().from(source).to(new TypeRef<Set<Integer>>() {});
          assertThat(result).isEqualTo(Sets.newHashSet(1, 2, 3));
        });

        it("can convert an array from one type to other",()->{
          double[] source = new double[]{1.1, 2.2, 3.3};
          int[] result = test().dsl().convert().from(source).to(new TypeRef<int[]>() {});
          assertThat(result).isEqualTo(new int[]{1, 2, 3});
        });

        it("can convert an array into a collection",()->{
          float[] source = new float[]{1.5f, 2.5f, 3.5f};
          List<Double> result = test().dsl().convert().from(source).to(new TypeRef<List<Double>>() {});
          assertThat(result).isEqualTo(Lists.newArrayList(1.5, 2.5, 3.5));
        });

      });

      describe("generators", () -> {
        it("can instantiate any niladic constructor object",()->{
          assertThat(test().dsl().generate().anInstanceOf(Object.class)).isInstanceOf(Object.class);
        });

        it("can generate arrays",()->{
          assertThat(test().dsl().convert().from(0).to(new TypeRef<List<String>[]>() {})).isInstanceOf(List[].class);
        });

        it("can generate queues",()->{
          assertThat(test().dsl().generate().anInstanceOf(Queue.class)).isInstanceOf(ConcurrentLinkedQueue.class);
          assertThat(test().dsl().generate().anInstanceOf(Deque.class)).isInstanceOf(LinkedList.class);
        });

        it("can generate maps",()->{
          assertThat(test().dsl().generate().anInstanceOf(Map.class)).isInstanceOf(HashMap.class);
        });

      });


    });

  }
}
