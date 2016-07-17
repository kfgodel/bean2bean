package net.sf.kfgodel.bean2bean.impl.repos.primitive.partials;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.api.optionals.Optional;
import net.sf.kfgodel.bean2bean.impl.transfunctions.primitives.StringFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;



/**
 * This type has string as a predefined source
 * Created by kfgodel on 05/07/16.
 */
public class StringDefinedPrimitiveTransfuctionDefinition implements SourceDefinedPrimitiveTransfunctionDefinition<String> {

  private Map<Class, Function> functionsByClass;

  public static StringDefinedPrimitiveTransfuctionDefinition create() {
    StringDefinedPrimitiveTransfuctionDefinition definition = new StringDefinedPrimitiveTransfuctionDefinition();
    return definition;
  }

  @Override
  public Function<java.lang.String, Long> toLong() {
    return StringFunction::toLong;
  }

  @Override
  public <T> Optional<Function<String, T>> toType(Class<T> destinationType) {
    Function<String, T> function = getFunctionsByClass().get(destinationType);
    return Nary.ofNullable(function);
  }

  public Map<Class, Function> getFunctionsByClass() {
    if (functionsByClass == null) {
      functionsByClass = initializeFunctions();
    }
    return functionsByClass;
  }

  private HashMap<Class, Function> initializeFunctions() {
    HashMap<Class, Function> functions = new HashMap<>();
    functions.put(Long.class, (Function<String, Long>) StringFunction::toLong);
    return functions;
  }
}
