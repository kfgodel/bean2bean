package info.kfgodel.bean2bean.dsl.api;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;

import java.util.function.Supplier;

/**
 * This type defines variable used on b2b tests
 * Date: 10/02/19 - 22:58
 */
public interface B2bTestContext extends TestContext {

  B2bDsl dsl();
  void dsl(Supplier<B2bDsl> definition);

  B2bDslConfig config();
  void config(Supplier<B2bDslConfig> definition);


}
