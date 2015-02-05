package net.sf.kfgodel.bean2bean;

import ar.com.dgarcia.javaspec.api.TestContext;
import net.sf.kfgodel.bean2bean.api.B2bApi;
import net.sf.kfgodel.bean2bean.impl.plans.TransformationRepository;

import java.util.function.Supplier;

/**
 * This type serves as context api definition for java specs
 * Created by kfgodel on 14/08/14.
 */
public interface B2bTestContext extends TestContext {

    void b2b(Supplier<B2bApi> definition);
    B2bApi b2b();
    
    TransformationRepository<Object> transRepo();
    void transRepo(Supplier<TransformationRepository> defintion);
}
