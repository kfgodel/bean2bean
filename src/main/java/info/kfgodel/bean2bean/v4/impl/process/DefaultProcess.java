package info.kfgodel.bean2bean.v4.impl.process;

import info.kfgodel.bean2bean.v4.api.exceptions.B2bException;
import info.kfgodel.bean2bean.v4.impl.engine.B2bEngine;
import info.kfgodel.bean2bean.v4.impl.intent.ConversionIntent;

import java.util.function.Function;

/**
 * This class is the default implementation for a conversion process
 * Date: 23/9/19 - 22:09
 */
public class DefaultProcess<O> implements ConversionProcess<O> {

  private ConversionIntent<O> intent;
  private Function<ConversionProcess<O>, O> converter;
  private B2bEngine engine;

  public static <O> DefaultProcess<O> create(ConversionIntent<O> intent, Function<ConversionProcess<O>, O> converter, B2bEngine engine) {
    DefaultProcess process = new DefaultProcess();
    process.intent = intent;
    process.converter = converter;
    process.engine = engine;
    return process;
  }

  @Override
  public O execute() {
    try{
      return converter.apply(this);
    }catch (B2bException knownException){
      throw knownException;
    } catch (Exception e){
      throw new B2bException("Converter function failed with: \"" + e.getMessage() + "\" when processing " + this, e);
    }
  }
}
