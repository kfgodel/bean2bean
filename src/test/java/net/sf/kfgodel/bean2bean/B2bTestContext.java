package net.sf.kfgodel.bean2bean;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import net.sf.kfgodel.bean2bean.api.B2bApi;
import net.sf.kfgodel.bean2bean.impl.engine.TransformationEngine;
import net.sf.kfgodel.bean2bean.impl.transformations.RuleRepository;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This type serves as context api definition for java specs
 * Created by kfgodel on 14/08/14.
 */
public interface B2bTestContext extends TestContext {

    void b2b(Supplier<B2bApi> definition);
    B2bApi b2b();
    
    RuleRepository<Object> ruleRepo();
    void ruleRepo(Supplier<RuleRepository> defintion);

    TransformationEngine engine();
    void engine(Supplier<TransformationEngine> definition);

    Function transfunction();
    void transfunction(Supplier<Function> definition);

}
