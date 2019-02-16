package info.kfgodel.bean2bean.other;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;

import java.util.function.Supplier;

/**
 * This type defines variables used on tests fro {@link TypeRef}
 * Date: 12/02/19 - 20:12
 */
public interface TypeRefTestContext extends TestContext {

  TypeRef typeRef();
  void typeRef(Supplier<TypeRef> definition);

  FunctionRef functionRef();
  void functionRef(Supplier<FunctionRef> definition);

  SupplierRef supplierRef();
  void supplierRef(Supplier<SupplierRef> definition);

  BiFunctionRef bifunctionRef();
  void bifunctionRef(Supplier<BiFunctionRef> definition);


}
