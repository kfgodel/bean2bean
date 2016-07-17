package net.sf.kfgodel.bean2bean.impl.repos.primitive;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.api.optionals.Optional;
import com.google.common.collect.Sets;
import net.sf.kfgodel.bean2bean.impl.repos.primitive.partials.SourceDefinedPrimitiveTransfunctionDefinition;
import net.sf.kfgodel.bean2bean.impl.repos.primitive.partials.StringDefinedPrimitiveTransfuctionDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * This type implements the transfunction definitions by different source types
 * Created by kfgodel on 05/07/16.
 */
public class PrimitiveTransfunctionRepoImpl implements PrimitiveTransfunctionRepo {

  private Map<Class, SourceDefinedPrimitiveTransfunctionDefinition> definitionByClass;

  public static PrimitiveTransfunctionRepoImpl create() {
    PrimitiveTransfunctionRepoImpl delta = new PrimitiveTransfunctionRepoImpl();
    return delta;
  }

  @Override
  public SourceDefinedPrimitiveTransfunctionDefinition<String> fromString() {
    return definitionForString();
  }

  @Override
  public <T> Optional<SourceDefinedPrimitiveTransfunctionDefinition<T>> fromType(Class<T> sourceType) {
    SourceDefinedPrimitiveTransfunctionDefinition<T> definition = getDefinitionByClass().get(sourceType);
    return Nary.ofNullable(definition);
  }

  @Override
  public Set<Class<?>> getPrimitiveTypes() {
    return Sets.newHashSet(Long.class, String.class);
  }

  public Map<Class, SourceDefinedPrimitiveTransfunctionDefinition> getDefinitionByClass() {
    if (definitionByClass == null) {
      definitionByClass = initializeDefinitions();
    }
    return definitionByClass;
  }

  private Map<Class, SourceDefinedPrimitiveTransfunctionDefinition> initializeDefinitions() {
    HashMap<Class, SourceDefinedPrimitiveTransfunctionDefinition> definition = new HashMap<>();
    definition.put(String.class, definitionForString());
    return definition;
  }

  private StringDefinedPrimitiveTransfuctionDefinition definitionForString() {
    return StringDefinedPrimitiveTransfuctionDefinition.create();
  }
}
