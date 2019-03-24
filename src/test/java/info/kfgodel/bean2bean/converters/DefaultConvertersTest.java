package info.kfgodel.bean2bean.converters;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import info.kfgodel.bean2bean.other.references.TypeRef;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

      describe("for numbers", () -> {
        it("can convert between primitive numeric types",()->{
          assertThat(test().dsl().convert().from(1).to(long.class)).isEqualTo(1L);
          assertThat(test().dsl().convert().from((short)3).to(byte.class)).isEqualTo((byte)3);
          assertThat(test().dsl().convert().from(2.5f).to(double.class)).isEqualTo(2.5);
          assertThat(test().dsl().convert().from("v").to(char.class)).isEqualTo('v');
          assertThat(test().dsl().convert().from("2.85").to(float.class)).isEqualTo(2.85f);
        });
        it("can convert between boxed numeric types",()->{
          assertThat(test().dsl().convert().from(Integer.valueOf(1)).to(Long.class)).isEqualTo(Long.valueOf(1L));
          assertThat(test().dsl().convert().from(Short.valueOf((short)3)).to(Byte.class)).isEqualTo(Byte.valueOf((byte)3));
          assertThat(test().dsl().convert().from(Float.valueOf(2.5f)).to(Double.class)).isEqualTo(Double.valueOf(2.5));
          assertThat(test().dsl().convert().from("v").to(Character.class)).isEqualTo(Character.valueOf('v'));
          assertThat(test().dsl().convert().from("2.85").to(Float.class)).isEqualTo(Float.valueOf(2.85f));
        });
      });

      describe("for dates and times", () -> {
        it("can convert to an iso string",()->{
          assertThat(test().dsl().convert().from(ZonedDateTime.of(2000,1,1,12,30,0,0, ZoneId.of("UCT"))).to(String.class))
            .isEqualTo("2000-01-01T12:30Z[UCT]");
          assertThat(test().dsl().convert().from(LocalDateTime.of(2000,1,1,12,30,0)).to(String.class))
            .isEqualTo("2000-01-01T12:30");
          assertThat(test().dsl().convert().from(LocalDate.of(2000,1,1)).to(String.class))
            .isEqualTo("2000-01-01");
          assertThat(test().dsl().convert().from(LocalTime.of(12,30,0)).to(String.class))
            .isEqualTo("12:30");
        });
        it("can convert from an iso string",()->{
          assertThat(test().dsl().convert().from("2000-01-01T12:30Z[UCT]").to(ZonedDateTime.class))
            .isEqualTo(ZonedDateTime.of(2000,1,1,12,30,0,0, ZoneId.of("UCT")));
          assertThat(test().dsl().convert().from("2000-01-01T12:30").to(LocalDateTime.class))
            .isEqualTo(LocalDateTime.of(2000,1,1,12,30,0));
          assertThat(test().dsl().convert().from("2000-01-01").to(LocalDate.class))
            .isEqualTo(LocalDate.of(2000,1,1));
          assertThat(test().dsl().convert().from("12:30").to(LocalTime.class))
            .isEqualTo(LocalTime.of(12,30,0));
        });
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

      describe("for json", () -> {
        it("can convert an object to json string",()->{
          List<Integer> source = Lists.newArrayList(1, 2, 3);
          String result = test().dsl().convert().from(source).to(String.class);
          assertThat(result).isEqualTo("[1,2,3]");
        });

        it("can convert a json string into an object",()->{
          String source = "[1,2,3]";
          List<Integer> result = test().dsl().convert().from(source).to(new TypeRef<List<Integer>>() {});
          assertThat(result).isEqualTo(Lists.newArrayList(1, 2, 3));
        });
      });

      describe("for enums", () -> {
        it("can convert an enum value into its name",()->{
          TestEnum source = TestEnum.SECOND_ENUM;
          String result = test().dsl().convert().from(source).to(String.class);
          assertThat(result).isEqualTo("SECOND_ENUM");
        });
        it("can convert an enum name into its value",()->{
          String source = "SECOND_ENUM";
          TestEnum result = test().dsl().convert().from(source).to(TestEnum.class);
          assertThat(result).isEqualTo(TestEnum.SECOND_ENUM);
        });
      });


    });

  }
}
