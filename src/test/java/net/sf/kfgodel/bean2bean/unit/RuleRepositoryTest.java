package net.sf.kfgodel.bean2bean.unit;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import net.sf.kfgodel.bean2bean.impl.transformations.TransformationRule;
import net.sf.kfgodel.bean2bean.impl.transformations.impl.ConditionedTransformationRule;
import net.sf.kfgodel.bean2bean.impl.transformations.impl.EmptyRepositoryException;
import net.sf.kfgodel.bean2bean.impl.transformations.impl.NoTransformationMatchesException;
import net.sf.kfgodel.bean2bean.impl.transformations.impl.SequentialRuleRepository;
import org.junit.runner.RunWith;

import java.util.function.Function;

import static net.sf.kfgodel.bean2bean.assertions.B2bAssertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Mockito.mock;

/**
 * This type verifies the behavior of a plan repository
 * Created by kfgodel on 04/02/15.
 */
@RunWith(JavaSpecRunner.class)
public class RuleRepositoryTest extends JavaSpec<B2bTestContext> {
    @Override
    public void define() {
        describe("a transformation repository", () -> {
            
            context().ruleRepo(SequentialRuleRepository::create);

            it("stores rules to transform objects", () -> {
                TransformationRule<Object> aRule = mock(TransformationRule.class);

                context().ruleRepo().store(aRule);
            });
            
            it("uses stored rules to get the best transformation for an object",()->{
                Function<Object, Object> aTransformation = mock(Function.class);
                context().ruleRepo().store(ConditionedTransformationRule.create((arg) -> true, aTransformation));

                Function<Object, Object> bestTransformation = context().ruleRepo().getBestTransformationFor(new Object());
                
                assertThat(bestTransformation).isSameAs(aTransformation);
            });
            
            it("throws an exception if repository has no rules when used",()->{
                try{
                    context().ruleRepo().getBestTransformationFor(1);
                    failBecauseExceptionWasNotThrown(EmptyRepositoryException.class);
                }catch(EmptyRepositoryException e){
                    assertThat(e.getMessage()).isEqualTo("Cannot get transformation for object[1] if repository is empty");
                }
            });
            
            it("throws an exception if no rule matches for a given object",()->{
                context().ruleRepo().store(ConditionedTransformationRule.create((arg) -> false, Function.identity()));

                try{
                    context().ruleRepo().getBestTransformationFor(1);
                    failBecauseExceptionWasNotThrown(NoTransformationMatchesException.class);
                }catch(NoTransformationMatchesException e){
                    assertThat(e.getMessage()).isEqualTo("There's no applicable transformation for object[1]");
                }
            });
            
            it("former rules have precedence over later stored rules",()->{
                Function<Object, Object> formerTransformation = mock(Function.class);
                Function<Object, Object> laterTransformation = mock(Function.class);
                
                context().ruleRepo().store(ConditionedTransformationRule.create((arg)-> true, formerTransformation));
                context().ruleRepo().store(ConditionedTransformationRule.create((arg) -> true, laterTransformation));

                Function<Object, Object> bestTransformation = context().ruleRepo().getBestTransformationFor(new Object());

                assertThat(bestTransformation).isSameAs(formerTransformation);
            });
            
            it("allows to transform different objects differently",()->{
                // Cast is necessary due to type inference limitation (tries to do Function<Object,Object> and fails)
                context().ruleRepo().store(ConditionedTransformationRule.create(String.class::isInstance, String::length));
                context().ruleRepo().store(ConditionedTransformationRule.create(Number.class::isInstance, Number::intValue));

                String aText = "Hola";
                Function<Object, Integer> textTransformation = context().ruleRepo().getBestTransformationFor(aText);
                assertThat(textTransformation.apply(aText)).isEqualTo(4);

                Double aNumber = 2.1;
                Function<Object, Integer> numberTransformation = context().ruleRepo().getBestTransformationFor(aNumber);
                assertThat(numberTransformation.apply(aNumber)).isEqualTo(2);
            });
            
        });

    }
}