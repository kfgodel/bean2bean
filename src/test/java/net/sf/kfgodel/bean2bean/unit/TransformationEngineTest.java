package net.sf.kfgodel.bean2bean.unit;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import net.sf.kfgodel.bean2bean.api.exceptions.Bean2beanException;
import net.sf.kfgodel.bean2bean.impl.engine.impl.EngineContextImpl;
import net.sf.kfgodel.bean2bean.impl.engine.impl.TransformationEngineImpl;
import org.junit.runner.RunWith;

import static net.sf.kfgodel.bean2bean.assertions.B2bAssertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

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
            
            it("uses a pre-defined transformation for two known objects",()->{
                
            }); 
            
            it("creates an new instance using reflection from void to a type",()->{
                
            }); 
            
            it("closes an instance from instance to void",()->{

            });
            
            it("creates a new instance and maps its properties from object to a type",()->{

            });
            
            it("uses a pre-defined hierarchy transformation from type to type",()->{
                
            });   
            
            it("fails with an exception if none of the above apply",()->{
                EngineContextImpl engineContext = EngineContextImpl.create(1, "2");

                try{
                    context().engine().getBestTransformationFor(engineContext);
                    failBecauseExceptionWasNotThrown(Bean2beanException.class);
                }catch(Bean2beanException e){
                    assertThat(e).hasMessage("There's no defined transformation from[1] to[\"2\"]");
                }
            });
            
        });

    }
}