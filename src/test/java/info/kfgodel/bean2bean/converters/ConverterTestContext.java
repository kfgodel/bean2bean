package info.kfgodel.bean2bean.converters;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;

import java.util.function.Supplier;

/**
 * This type defines variables to be used on converter tests
 * Date: 28/02/19 - 19:48
 */
public interface ConverterTestContext extends TestContext {

  B2bDsl dsl();
  void dsl(Supplier<B2bDsl> definition);

}
