package info.kfgodel.bean2bean.v3.converters;

import info.kfgodel.bean2bean.v3.converters.collections.Array2ArrayConverter;
import info.kfgodel.bean2bean.v3.converters.collections.Array2CollectionConverter;
import info.kfgodel.bean2bean.v3.converters.collections.Collection2CollectionConverter;
import info.kfgodel.bean2bean.v3.converters.datetimes.String2LocalDateConverter;
import info.kfgodel.bean2bean.v3.converters.datetimes.String2LocalDateTimeConverter;
import info.kfgodel.bean2bean.v3.converters.datetimes.String2LocalTimeConverter;
import info.kfgodel.bean2bean.v3.converters.datetimes.String2ZonedDateTimeConverter;
import info.kfgodel.bean2bean.v3.converters.datetimes.Temporal2StringConverter;
import info.kfgodel.bean2bean.v3.converters.enums.Enum2StringConverter;
import info.kfgodel.bean2bean.v3.converters.enums.String2EnumConverter;
import info.kfgodel.bean2bean.v3.converters.generators.ArrayInstantiator;
import info.kfgodel.bean2bean.v3.converters.generators.GenericInstantiator;
import info.kfgodel.bean2bean.v3.converters.json.JsonString2ObjectConverter;
import info.kfgodel.bean2bean.v3.converters.json.Object2JsonStringConverter;
import info.kfgodel.bean2bean.v3.converters.optionals.Object2OptionalConverter;
import info.kfgodel.bean2bean.v3.converters.optionals.Optional2ObjectConverter;
import info.kfgodel.bean2bean.v3.dsl.api.ConfigureDsl;
import info.kfgodel.bean2bean.v3.dsl.api.Nothing;
import info.kfgodel.bean2bean.v3.other.references.SupplierRef;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This class represents the builtin converter configurator that knows how to add converters to a dsl
 * so the user doesn't have to do it manually
 *
 * Date: 24/03/19 - 14:30
 */
public class DefaultConfigurator {

  public static DefaultConfigurator create() {
    DefaultConfigurator configurator = new DefaultConfigurator();
    return configurator;
  }

  public void addAllTo(ConfigureDsl configure) {
    addDatetimesTo(configure);
    addCollectionsTo(configure);
    addOptionalsTo(configure);
    addEnumsTo(configure);
    addGeneratorsTo(configure);
    addPrimitivesTo(configure);
    addNoConversionTo(configure); // This should be one of the last
    addJsonTo(configure);  // Due to it's generality we keep this to the very last
  }

  private void addPrimitivesTo(ConfigureDsl configure) {
    PrimitonConverters.registerOn(configure);
  }

  private void addJsonTo(ConfigureDsl configure) {
    configure.scopingWith(Object2JsonStringConverter::shouldBeUsed).useConverter(Object2JsonStringConverter.create());
    // Because string is an object too, we want this to be after the previous- Doing that a string 2 string conversion
    // is interpreted a conversion to json, and not backwards
    configure.scopingWith(JsonString2ObjectConverter::shouldBeUsed).useConverter(JsonString2ObjectConverter.create());
  }

  private void addEnumsTo(ConfigureDsl configure) {
    configure.useConverter(Enum2StringConverter.create());
    configure.useConverter(String2EnumConverter.create());
  }

  private void addOptionalsTo(ConfigureDsl configure) {
    configure.useConverter(Object2OptionalConverter.create());
    configure.useConverter(Optional2ObjectConverter.create());
  }

  private void addDatetimesTo(ConfigureDsl configure) {
    configure.useConverter(Temporal2StringConverter.create());
    configure.useConverter(String2ZonedDateTimeConverter.create());
    configure.useConverter(String2LocalDateTimeConverter.create());
    configure.useConverter(String2LocalDateConverter.create());
    configure.useConverter(String2LocalTimeConverter.create());
  }

  private void addGeneratorsTo(ConfigureDsl configure) {
    configure.scopingTo().accept(Nothing.class).andProduce(Set.class)
      .useConverter(new SupplierRef<Set>(HashSet::new) {});
    configure.scopingTo().accept(Nothing.class).andProduce(List.class)
      .useConverter(new SupplierRef<List>(ArrayList::new) {});
    configure.scopingTo().accept(Nothing.class).andProduce(Queue.class)
      .useConverter(new SupplierRef<Queue>(ConcurrentLinkedQueue::new) {});
    configure.scopingTo().accept(Nothing.class).andProduce(Deque.class)
      .useConverter(new SupplierRef<Deque>(LinkedList::new) {});
    configure.scopingTo().accept(Nothing.class).andProduce(Map.class)
      .useConverter(new SupplierRef<Map>(HashMap::new) {});
    configure.useConverter(GenericInstantiator.create());
    configure.scopingWith(ArrayInstantiator::shouldBeUsed).useConverter(ArrayInstantiator.create());
  }

  private void addCollectionsTo(ConfigureDsl configure) {
    configure.useConverter(Collection2CollectionConverter.create());
    configure.scopingWith(Array2ArrayConverter::shouldBeUsed).useConverter(Array2ArrayConverter.create());
    configure.scopingWith(Array2CollectionConverter::shouldBeUsed).useConverter(Array2CollectionConverter.create());
  }

  private void addNoConversionTo(ConfigureDsl configure) {
    configure.scopingWith(NoConversionConverter::shouldBeUsed).useConverter(NoConversionConverter.create());
  }
}
