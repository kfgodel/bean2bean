package info.kfgodel.reflect;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import info.kfgodel.bean2bean.v3.other.references.BiFunctionRef;
import info.kfgodel.bean2bean.v3.other.references.ConsumerRef;
import info.kfgodel.bean2bean.v3.other.references.FunctionRef;
import info.kfgodel.bean2bean.v3.other.references.SupplierRef;
import info.kfgodel.reflect.references.TypeRef;
import info.kfgodel.reflect.types.SupertypeSpliterator;
import info.kfgodel.reflect.types.binding.BoundType;
import info.kfgodel.reflect.types.extraction.TypeArgumentExtractor;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Supplier;

/**
 * This type defines variables used on tests fro {@link TypeRef}
 * Date: 12/02/19 - 20:12
 */
public interface ReflectTestContext extends TestContext {

  TypeRef typeRef();
  void typeRef(Supplier<TypeRef> definition);

  FunctionRef functionRef();
  void functionRef(Supplier<FunctionRef> definition);

  SupplierRef supplierRef();
  void supplierRef(Supplier<SupplierRef> definition);

  BiFunctionRef bifunctionRef();
  void bifunctionRef(Supplier<BiFunctionRef> definition);

  ConsumerRef consumerRef();
  void consumerRef(Supplier<ConsumerRef> definition);

  SupertypeSpliterator spliterator();
  void spliterator(Supplier<SupertypeSpliterator> definition);

  Type type();
  void type(Supplier<Type> definition);

  List<String> supertypes();
  void supertypes(Supplier<List<String>> definition);

  TypeArgumentExtractor argumentExtractor();
  void argumentExtractor(Supplier<TypeArgumentExtractor> definition);

  BoundType boundType();
  void boundType(Supplier<BoundType> definition);

}
