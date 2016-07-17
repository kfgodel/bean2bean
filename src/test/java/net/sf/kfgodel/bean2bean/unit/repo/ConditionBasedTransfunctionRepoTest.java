package net.sf.kfgodel.bean2bean.unit.repo;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.nary.api.optionals.Optional;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import net.sf.kfgodel.bean2bean.impl.repos.condition.ConditionBasedTransfunctionRepoImpl;
import net.sf.kfgodel.bean2bean.impl.repos.condition.TransformationIntention;
import net.sf.kfgodel.bean2bean.impl.repos.condition.TransformationIntentionImpl;
import net.sf.kfgodel.bean2bean.impl.repos.condition.predicates.ExpectingAnInstance;
import net.sf.kfgodel.bean2bean.impl.repos.condition.predicates.IsAnInstance;
import net.sf.kfgodel.bean2bean.impl.repos.condition.predicates.builder.PredicateBuilder;
import net.sf.kfgodel.bean2bean.impl.repos.primitive.PrimitiveTransfunctionRepoImpl;
import org.junit.runner.RunWith;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the correct behavior for a conditioned repo
 * Created by kfgodel on 16/07/16.
 */
@RunWith(JavaSpecRunner.class)
public class ConditionBasedTransfunctionRepoTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a condition based repo", () -> {
      context().conditionRepo(ConditionBasedTransfunctionRepoImpl::create);

      it("selects the first transfunction whose condition is applicable to the given transformation intention",()->{
        Function<String, Long> storedTransfunction = PrimitiveTransfunctionRepoImpl.create().fromString().toLong();
        context().conditionRepo().store(PredicateBuilder.where()
          .source(IsAnInstance.of(String.class))
          .andDestination(ExpectingAnInstance.of(Long.class)), storedTransfunction);

        TransformationIntention transformationIntention = TransformationIntentionImpl.create("3", ExpectingAnInstance.of(Long.class));
        Optional<Function<String, Long>> foundTransfunction = context().conditionRepo().findTransfunctionFor(transformationIntention);

        assertThat(foundTransfunction.get()).isSameAs(storedTransfunction);
      });   
      

    });

  }
}