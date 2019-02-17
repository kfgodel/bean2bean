package info.kfgodel.bean2bean.other;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 17/02/19 - 13:27
 */
@RunWith(JavaSpecRunner.class)
public class ConsumerRefTest extends JavaSpec<TypeRefTestContext> {
  public static Logger LOG = LoggerFactory.getLogger(ConsumerRefTest.class);

  @Override
  public void define() {
    describe("a consumer reference", () -> {
      test().consumerRef(()->
        new ConsumerRef<String>((input)-> LOG.info(input)){}
      );

      it("allows access to the input type argument",()->{
        assertThat(test().consumerRef().getInputType()).isEqualTo(String.class);
      });

      it("allows access to the consumer",()->{
        Consumer<String> consumer = test().consumerRef().getConsumer();
        assertThat(consumer).isNotNull();
      });

    });

  }
}