package info.kfgodel.bean2bean.dsl.api;

import info.kfgodel.bean2bean.core.api.Bean2bean;

/**
 * This type represents the a dsl to interact with bean2bean without knowing its internals
 *
 * Date: 10/02/19 - 22:57
 */
public interface B2bDsl {
  /**
   * Initiates a conversion operation, transforming an input into an output
   * @return A conversion specific DSL
   */
  ConvertDsl convert();

  /**
   * Initiates a creation operation, where input is not needed to get an output
   * @return A creation specific DSL
   */
  CreateDsl make();


  /**
   * Allows access to the configuration of b2b state and behavior
   * @return The dsl instanace to configura b2b with
   */
  ConfigureDsl configure();


  /**
   * Allows access to the core instance of bean2bean that handles the actual conversions.<br>
   *   This core has a minimalistic interface and it's not as usable as the dsl
   * @return The core instance for direct manipulation
   */
  Bean2bean getCore();

}
