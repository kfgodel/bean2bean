package net.sf.kfgodel.bean2bean.unit;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import net.sf.kfgodel.bean2bean.impl.plans.TransformationRule;
import net.sf.kfgodel.bean2bean.impl.plans.impl.EmptyRepositoryException;
import net.sf.kfgodel.bean2bean.impl.plans.impl.NoTransformationMatchesException;
import net.sf.kfgodel.bean2bean.impl.plans.impl.SequentialRuleRepository;
import org.junit.runner.RunWith;

import java.util.function.Function;
import java.util.function.Predicate;

import static net.sf.kfgodel.bean2bean.assertions.B2bAssertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Mockito.mock;

/**
 * This type verifies the behavior of a plan repository
 * Created by kfgodel on 04/02/15.
 */
@RunWith(JavaSpecRunner.class)
public class TransformationRepositoryTest extends JavaSpec<B2bTestContext> {
    @Override
    public void define() {
        describe("a transformation repository", () -> {
            
            context().transRepo(SequentialRuleRepository::create);

            it("stores conditional transformations as rules", () -> {
                Function<Object, Object> aTransformation = mock(Function.class);
                Predicate<Object> aCondition = mock(Predicate.class);

                TransformationRule rule = context().transRepo().storeUnder(aCondition, aTransformation);

                assertThat(rule).isNotNull();
            });
            
            it("uses stored rules to get the best transformation for an object",()->{
                Function<Object, Object> aTransformation = mock(Function.class);
                context().transRepo().storeUnder((arg)-> true, aTransformation);

                Function<Object, Object> bestTransformation = context().transRepo().getBestTransformationFor(new Object());
                
                assertThat(bestTransformation).isSameAs(aTransformation);
            });
            
            it("throws an exception if repository has no rules when used",()->{
                try{
                    context().transRepo().getBestTransformationFor(1);
                    failBecauseExceptionWasNotThrown(EmptyRepositoryException.class);
                }catch(EmptyRepositoryException e){
                    assertThat(e.getMessage()).isEqualTo("Cannot get transformation for object[1] if repository is empty");
                }
            });
            
            it("throws an exception if no rule matches for a given object",()->{
                context().transRepo().storeUnder((arg) -> false, Function.identity());

                try{
                    context().transRepo().getBestTransformationFor(1);
                    failBecauseExceptionWasNotThrown(NoTransformationMatchesException.class);
                }catch(NoTransformationMatchesException e){
                    assertThat(e.getMessage()).isEqualTo("There's no applicable transformation for object[1]");
                }
            });
            
            it("former rules have precedence over later stored rules",()->{
                Function<Object, Object> formerTransformation = mock(Function.class);
                Function<Object, Object> laterTransformation = mock(Function.class);
                
                context().transRepo().storeUnder((arg)-> true, formerTransformation);
                context().transRepo().storeUnder((arg)-> true, laterTransformation);

                Function<Object, Object> bestTransformation = context().transRepo().getBestTransformationFor(new Object());

                assertThat(bestTransformation).isSameAs(formerTransformation);
            });
            
            it("allows to transform different objects differently",()->{
                context().transRepo().storeUnder(String.class::isInstance, (Function<String, Integer>)String::length);
                context().transRepo().storeUnder(Number.class::isInstance, (Function<Number, Integer>)Number::intValue);

                String aText = "Hola";
                Function<Object, Integer> textTransformation = context().transRepo().getBestTransformationFor(aText);
                assertThat(textTransformation.apply(aText)).isEqualTo(4);

                Double aNumber = 2.1;
                Function<Object, Integer> numberTransformation = context().transRepo().getBestTransformationFor(aNumber);
                assertThat(numberTransformation.apply(aNumber)).isEqualTo(2);
            });
            
        });

    }
}