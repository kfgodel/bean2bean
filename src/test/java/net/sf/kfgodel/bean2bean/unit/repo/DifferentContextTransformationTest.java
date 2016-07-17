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
import net.sf.kfgodel.bean2bean.impl.repos.condition.trans.OnlySourceAndDestTransfunction;
import net.sf.kfgodel.bean2bean.impl.repos.condition.trans.OnlySourceTransfunction;
import net.sf.kfgodel.bean2bean.impl.repos.primitive.PrimitiveTransfunctionRepoImpl;
import org.junit.runner.RunWith;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class verifies the setup required to define transformations for different level of context needed
 * Created by kfgodel on 17/07/16.
 */
@RunWith(JavaSpecRunner.class)
public class DifferentContextTransformationTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("using a condition repo", () -> {
      context().conditionRepo(ConditionBasedTransfunctionRepoImpl::create);

      it("a transformation requiring only the source object can be defined",()->{
        Function<TransformationIntention, Long> storedTransfunction = OnlySourceTransfunction.create(PrimitiveTransfunctionRepoImpl.create().fromString().toLong());
        context().conditionRepo().store(PredicateBuilder.where()
          .source(IsAnInstance.of(String.class))
          .andDestination(ExpectingAnInstance.of(Long.class)), storedTransfunction);

        TransformationIntention transformationIntention = TransformationIntentionImpl.create("3", ExpectingAnInstance.of(Long.class));
        Optional<Function<TransformationIntention, Long>> foundTransfunction = context().conditionRepo().findTransfunctionFor(transformationIntention);

        assertThat(foundTransfunction.get().apply(transformationIntention)).isEqualTo(3L);
      });

      it("a transformation requiring source and destination can be defined",()->{
        Function<TransformationIntention, Object> storedTransfunction = OnlySourceAndDestTransfunction.create((source, destination)->{
          ExpectingAnInstance expecting = (ExpectingAnInstance) destination;
          Optional<Function<Object, Object>> transfunction = PrimitiveTransfunctionRepoImpl.create()
            .fromType(source.getClass()).get()
            .toType((Class)expecting.getExpectedType());
          return transfunction.get();
        });
        context().conditionRepo().store(PredicateBuilder.where()
          .source(IsAnInstance.ofAny(PrimitiveTransfunctionRepoImpl.create().getPrimitiveTypes()))
          .andDestination(ExpectingAnInstance.ofAny(PrimitiveTransfunctionRepoImpl.create().getPrimitiveTypes())), storedTransfunction);

        TransformationIntention transformationIntention = TransformationIntentionImpl.create("3", ExpectingAnInstance.of(Long.class));
        Optional<Function<TransformationIntention, Long>> foundTransfunction = context().conditionRepo().findTransfunctionFor(transformationIntention);

        assertThat(foundTransfunction.get().apply(transformationIntention)).isEqualTo(3L);

      });

    });

  }
}