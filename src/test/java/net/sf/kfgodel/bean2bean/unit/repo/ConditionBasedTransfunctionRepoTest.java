package net.sf.kfgodel.bean2bean.unit.repo;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.nary.api.optionals.Optional;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import net.sf.kfgodel.bean2bean.impl.repos.condition.ConditionBasedTransfunctionRepoImpl;
import net.sf.kfgodel.bean2bean.impl.repos.condition.TransformationIntention;
import org.junit.runner.RunWith;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

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
        Function<TransformationIntention, Object> storedTransfunction = mock(Function.class);
        context().conditionRepo().store((intention)-> true, storedTransfunction);

        Optional<Function<TransformationIntention, Object>> foundTransfunction = context().conditionRepo().findTransfunctionFor(mock(TransformationIntention.class));

        assertThat(foundTransfunction.get()).isSameAs(storedTransfunction);
      });   
      
      it("returns an empty optional if none matches",()->{
        context().conditionRepo().store((intention)-> false, mock(Function.class));

        Optional<Function<TransformationIntention, Object>> foundTransfunction = context().conditionRepo().findTransfunctionFor(mock(TransformationIntention.class));

        assertThat(foundTransfunction.isAbsent()).isTrue();
      });   
      

    });

  }
}