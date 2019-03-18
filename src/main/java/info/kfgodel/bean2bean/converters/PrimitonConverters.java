package info.kfgodel.bean2bean.converters;

import ar.com.kfgodel.nary.api.optionals.Optional;
import ar.com.kfgodel.primitons.api.Primiton;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;

import java.util.function.Function;

/**
 * This class is not really a converter but a way to register multiple converters using the
 * primiton functions
 *
 * Date: 17/03/19 - 20:04
 */
public class PrimitonConverters {

  /**
   * Registers all the available primiton converters on the given dsl
   * @param dsl The dsl to register the converters on
   */
  public static void registerOn(B2bDsl dsl) {
    for (Class<?> sourceType : Primiton.types().allTypes()) {
      for (Class<?> targetType : Primiton.types().allTypes()) {
        registerOn(dsl, sourceType, targetType);
      }
    }
  }

  private static void registerOn(B2bDsl dsl, Class<?> sourceType, Class<?> targetType) {
    Optional<? extends Function<?, ?>> found = Primiton.converterFrom(sourceType, targetType);
    found.ifPresent((Function converterFunction) -> {
      dsl.configure().scopingTo().accept(sourceType).andProduce(targetType)
        .useConverter(converterFunction);
    });
  }
}
