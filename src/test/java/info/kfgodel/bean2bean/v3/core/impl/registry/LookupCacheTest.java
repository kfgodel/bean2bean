package info.kfgodel.bean2bean.v3.core.impl.registry;

import info.kfgodel.bean2bean.v3.dsl.api.B2bTestContext;
import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class verifies the basic behavior of a lookup cache
 * Date: 18/02/19 - 23:46
 */
@RunWith(JavaSpecRunner.class)
public class LookupCacheTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a lookup cache", () -> {
      test().cache(LookupCache::create);

      describe("when created", () -> {
        it("is empty", () -> {
          assertThat(test().cache().size()).isEqualTo(0);
        });
      });

      describe("when a key is retrieved", () -> {

        describe("if it's the first time", () -> {
          it("produces a value and caches it, linked to the key",()->{
            String producedValue = test().cache().retrieveCachedOrProduceFor("a key", (key) -> "a value");
            assertThat(producedValue).isEqualTo("a value");
          });
        });

        describe("if it's not the first time", () -> {
          beforeEach(()->{
            test().cache().retrieveCachedOrProduceFor("a key", (key) -> "first value");
          });

          it("retrieves the previously stored value",()->{
            String producedValue = test().cache().retrieveCachedOrProduceFor("a key", (key) -> "second value");
            assertThat(producedValue).isEqualTo("first value");
          });

          it("produces a new one if invalidated",()->{
            test().cache().invalidate();

            String producedValue = test().cache().retrieveCachedOrProduceFor("a key", (key) -> "second value");
            assertThat(producedValue).isEqualTo("second value");
          });
        });
      });

    });
  }
}