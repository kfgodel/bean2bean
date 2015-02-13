package net.sf.kfgodel.bean2bean.unit;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.diamond.api.Diamond;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import net.sf.kfgodel.bean2bean.api.exceptions.Bean2beanException;
import net.sf.kfgodel.bean2bean.impl.engine.EngineContext;
import net.sf.kfgodel.bean2bean.impl.engine.impl.EngineContextImpl;
import net.sf.kfgodel.bean2bean.impl.engine.impl.TransformationEngineImpl;
import net.sf.kfgodel.bean2bean.impl.engine.impl.trans.CloseResourceTransformation;
import net.sf.kfgodel.bean2bean.impl.engine.impl.trans.InstantiateTransformation;
import net.sf.kfgodel.bean2bean.impl.mappings.MappingRepository;
import net.sf.kfgodel.bean2bean.impl.mappings.impl.MappingRepositoryImpl;
import net.sf.kfgodel.bean2bean.impl.mappings.impl.MappingVectorImpl;
import org.junit.runner.RunWith;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;

import static net.sf.kfgodel.bean2bean.assertions.B2bAssertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * This type verifies the behavior of the transformation engine and its rules
 * Created by kfgodel on 08/02/15.
 */
@RunWith(JavaSpecRunner.class)
public class TransformationEngineTest extends JavaSpec<B2bTestContext> {
    @Override
    public void define() {
        describe("the transformation engine", () -> {
            
            context().engine(TransformationEngineImpl::create);
            
            it("uses a pre-defined transformation for a known vector",()->{
                MappingRepository mappingRepo = MappingRepositoryImpl.create();
                mappingRepo.storeFor(MappingVectorImpl.create(1,"2"), (arg)-> "tres");

                EngineContextImpl engineContext = EngineContextImpl.create(1, "2", mappingRepo);
                Function<EngineContext, Object> transformation = context().engine().getBestTransformationFor(engineContext);
                Object result = transformation.apply(engineContext);
                
                assertThat(result).isEqualTo("tres");
            });
            
            it("creates an new instance using reflection from void to a type",()->{
                MappingRepository mappingRepo = MappingRepositoryImpl.create();
                InstantiateTransformation.registerIn(mappingRepo);

                EngineContextImpl engineContext = EngineContextImpl.create(Diamond.of(void.class), Diamond.of(ArrayList.class), mappingRepo);
                Function<EngineContext, Object> transformation = context().engine().getBestTransformationFor(engineContext);
                Object result = transformation.apply(engineContext);

                assertThat(result).isNotNull();
                assertThat(result.getClass()).isEqualTo(ArrayList.class);
            });
            
            it("closes a instance from closeable to void",()->{
                Closeable resource = mock(Closeable.class);

                MappingRepository mappingRepo = MappingRepositoryImpl.create();
                CloseResourceTransformation.registerIn(mappingRepo);

                EngineContextImpl engineContext = EngineContextImpl.create(resource, Diamond.of(void.class), mappingRepo);
                Function<EngineContext, Object> transformation = context().engine().getBestTransformationFor(engineContext);
                transformation.apply(engineContext);

                try {
                    verify(resource).close();
                } catch (IOException e) {
                    throw new RuntimeException("Mockito throwing exception?");
                }
            });
            
            it("creates a new instance and maps its properties from object to a type",()->{

            });
            
            it("looks for the hierarchy of source and destination to find a suitable transformation",()->{
                MappingRepository mappingRepo = MappingRepositoryImpl.create();
                mappingRepo.storeFor(MappingVectorImpl.create(Diamond.of(Integer.class),Diamond.of(String.class)), (arg)-> "cuatro");

                EngineContextImpl engineContext = EngineContextImpl.create(1, "2", mappingRepo);
                Function<EngineContext, Object> transformation = context().engine().getBestTransformationFor(engineContext);
                Object result = transformation.apply(engineContext);

                assertThat(result).isEqualTo("cuatro");

            });   
            
            it("fails with an exception if none of the above apply",()->{
                MappingRepository mappingRepo = MappingRepositoryImpl.create();
                EngineContextImpl engineContext = EngineContextImpl.create(1, "2", mappingRepo);

                try{
                    context().engine().getBestTransformationFor(engineContext);
                    failBecauseExceptionWasNotThrown(Bean2beanException.class);
                }catch(Bean2beanException e){
                    assertThat(e).hasMessage("There's no transformation defined from[1] to[\"2\"]");
                }
            });
            
        });

    }
}