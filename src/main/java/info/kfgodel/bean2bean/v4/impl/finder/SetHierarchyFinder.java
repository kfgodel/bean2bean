package info.kfgodel.bean2bean.v4.impl.finder;

import ar.com.kfgodel.nary.api.optionals.Optional;
import info.kfgodel.bean2bean.v4.impl.intent.ConversionIntent;
import info.kfgodel.bean2bean.v4.impl.intent.Intent;
import info.kfgodel.bean2bean.v4.impl.process.ConversionProcess;
import info.kfgodel.bean2bean.v4.impl.sets.Set;
import info.kfgodel.bean2bean.v4.impl.store.ConverterStore;
import info.kfgodel.bean2bean.v4.impl.vector.ConversionVector;
import info.kfgodel.bean2bean.v4.impl.vector.Vector;

import java.util.Iterator;
import java.util.function.Function;

/**
 * This class implements a finder that looks for a converter using the set hierarchy of the conversion vector.<br>
 *   It tries to find a more general converter after it fails looking for a more specific. It uses the super sets
 *   of the original set to widen the search for a suitable converter.<br>
 *  <br>
 *  This finder is only applicable for conversion vectors that have sets as source and target
 *
 * Date: 28/9/19 - 20:45
 */
public class SetHierarchyFinder implements ConverterFunctionFinder {

  private ExactVectorFinder exactFinder;

  @Override
  public <O> Optional<Function<ConversionProcess<O>, O>> findBestConverterFor(ConversionIntent<O> intent) {
    final ConversionVector conversionVector = intent.getVector();
    final Object source = conversionVector.getSource();
    final Object target = conversionVector.getTarget();
    if (!(source instanceof Set) || !(target instanceof Set)){
      // This finder only works on sets
      return Optional.empty();
    }
    return findBestConvertForHierarchiesOf((Set) source,  (Set) target, intent);
  }

  private <O> Optional<Function<ConversionProcess<O>, O>> findBestConvertForHierarchiesOf(Set source, Set target, ConversionIntent<O> intent) {
    Iterator<Set> targetSetHierarchy = target.getSuperSets().iterator();
    while (targetSetHierarchy.hasNext()) {
      Set targetSet = targetSetHierarchy.next();

      Iterator<Set> sourceSetHierarchy = source.getSuperSets().iterator();
      while (sourceSetHierarchy.hasNext()) {
        Set sourceSet = sourceSetHierarchy.next();

        final Intent conversionIntent = Intent.create(intent.getInput(), Vector.create(sourceSet, targetSet));
        final Optional found = exactFinder.findBestConverterFor(conversionIntent);
        if (found.isPresent()) {
          return found;
        }
      }
    }
    return Optional.empty();
  }

  public static SetHierarchyFinder create(ConverterStore store) {
    SetHierarchyFinder finder = new SetHierarchyFinder();
    finder.exactFinder = ExactVectorFinder.create(store);
    return finder;
  }

}
