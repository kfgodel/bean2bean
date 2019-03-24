package info.kfgodel.bean2bean.converters;

import ar.com.kfgodel.nary.api.optionals.Optional;
import ar.com.kfgodel.primitons.api.Primiton;
import info.kfgodel.bean2bean.dsl.api.ConfigureDsl;

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
   * @param configure The dsl to register the converters on
   */
  public static void registerOn(ConfigureDsl configure) {
    for (Class<?> sourceType : Primiton.types().allTypes()) {
      for (Class<?> targetType : Primiton.types().allTypes()) {
        if(shouldBeIgnored(sourceType, targetType)){
          continue;
        }
        registerOn(configure, sourceType, targetType);
      }
    }
  }

  private static boolean shouldBeIgnored(Class<?> sourceType, Class<?> targetType) {
    if (Object.class.equals(sourceType) && String.class.equals(targetType)) {
      // We don't want the default Object.toString() conversion
      return true;
    }
    if (Object.class.equals(sourceType) && Object.class.equals(targetType)) {
      // We don't want the default identity function as a vector converter
      return true;
    }
    return false;
  }

  private static void registerOn(ConfigureDsl configure, Class<?> sourceType, Class<?> targetType) {
    Optional<? extends Function<?, ?>> found = Primiton.converterFrom(sourceType, targetType);
    found.ifPresent((Function converterFunction) -> {
      configure.scopingTo().accept(sourceType).andProduce(targetType)
        .useConverter(converterFunction);
    });
  }
}
