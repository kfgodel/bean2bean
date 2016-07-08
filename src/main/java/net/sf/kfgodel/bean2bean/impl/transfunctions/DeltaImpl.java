package net.sf.kfgodel.bean2bean.impl.transfunctions;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.api.optionals.Optional;
import net.sf.kfgodel.bean2bean.impl.transfunctions.partials.SourceDefinedTransfunctionDefinition;
import net.sf.kfgodel.bean2bean.impl.transfunctions.partials.StringDefinedTransfuctionDefinition;

import java.util.HashMap;
import java.util.Map;



/**
 * This type implements the transfunction definitions by different source types
 * Created by kfgodel on 05/07/16.
 */
public class DeltaImpl implements Delta {

  private Map<Class, SourceDefinedTransfunctionDefinition> definitionByClass;

  public static DeltaImpl create() {
    DeltaImpl delta = new DeltaImpl();
    return delta;
  }

  @Override
  public SourceDefinedTransfunctionDefinition<String> fromString() {
    return definitionForString();
  }

  @Override
  public <T> Optional<SourceDefinedTransfunctionDefinition<T>> fromType(Class<T> sourceType) {
    SourceDefinedTransfunctionDefinition<T> definition = getDefinitionByClass().get(sourceType);
    return Nary.ofNullable(definition);
  }

  public Map<Class, SourceDefinedTransfunctionDefinition> getDefinitionByClass() {
    if (definitionByClass == null) {
      definitionByClass = initializeDefinitions();
    }
    return definitionByClass;
  }

  private Map<Class, SourceDefinedTransfunctionDefinition> initializeDefinitions() {
    HashMap<Class, SourceDefinedTransfunctionDefinition> definition = new HashMap<>();
    definition.put(String.class, definitionForString());
    return definition;
  }

  private StringDefinedTransfuctionDefinition definitionForString() {
    return StringDefinedTransfuctionDefinition.create();
  }
}
