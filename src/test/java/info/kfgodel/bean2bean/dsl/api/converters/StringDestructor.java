package info.kfgodel.bean2bean.dsl.api.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * Example class for a destructor on tests
 * Date: 17/02/19 - 13:10
 */
public class StringDestructor implements Consumer<String> {
  public static Logger LOG = LoggerFactory.getLogger(StringDestructor.class);

  @Override
  public void accept(String input) {
    LOG.info("Destroying \"{}\"", input);
  }

  public static StringDestructor create() {
    StringDestructor destructor = new StringDestructor();
    return destructor;
  }

}
