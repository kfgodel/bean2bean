package info.kfgodel.bean2bean.v4.impl.sets;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.v4.B2bTestContext;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Date: 28/9/19 - 19:40
 */
@RunWith(JavaSpecRunner.class)
public class TypeBasedHierachyTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a type based set", () -> {
      test().typeSet(()-> TypeBasedSet.create(List.class, String.class));


    });

  }
}