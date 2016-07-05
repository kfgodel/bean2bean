package net.sf.kfgodel.bean2bean.impl.transfunctions.partials;

import net.sf.kfgodel.bean2bean.api.exceptions.Bean2beanException;

import java.util.function.Function;

/**
 * This type has string as a predefined source
 * Created by kfgodel on 05/07/16.
 */
public class StringDefinedTransfuctionDefinition implements SourceDefinedTransfunctionDefinition<String> {

  public static StringDefinedTransfuctionDefinition create() {
    StringDefinedTransfuctionDefinition definition = new StringDefinedTransfuctionDefinition();
    return definition;
  }

  @Override
  public Function<String, Long> toLong() {
    return (string)->{
      try {
        return Long.valueOf(string);
      } catch (NumberFormatException e) {
        throw new Bean2beanException("Cannot convert the String [" + string + "] to Long: Unable to parse it");
      }
    };
  }
}
