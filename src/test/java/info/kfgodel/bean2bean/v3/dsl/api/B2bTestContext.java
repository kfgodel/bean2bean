package info.kfgodel.bean2bean.v3.dsl.api;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import info.kfgodel.bean2bean.v3.core.api.registry.Bean2BeanRegistry;
import info.kfgodel.bean2bean.v3.core.api.registry.Domain;
import info.kfgodel.bean2bean.v3.core.impl.registry.LookupCache;
import info.kfgodel.bean2bean.v3.core.impl.registry.domains.DomainCalculator;
import info.kfgodel.bean2bean.v3.dsl.api.example.TestPerson;

import java.util.function.Supplier;

/**
 * This type defines variable used on b2b tests
 * Date: 10/02/19 - 22:58
 */
public interface B2bTestContext extends TestContext {

  B2bDsl dsl();
  void dsl(Supplier<B2bDsl> definition);

  DomainCalculator calculator();
  void calculator(Supplier<DomainCalculator> definition);

  Domain domain();
  void domain(Supplier<Domain> definition);

  Bean2BeanRegistry registry();
  void registry(Supplier<Bean2BeanRegistry> definition);

  LookupCache cache();
  void cache(Supplier<LookupCache> definition);

  ConfigureDsl configure();
  void configure(Supplier<ConfigureDsl> definition);

  TestPerson person();
  void person(Supplier<TestPerson> definition);


}
