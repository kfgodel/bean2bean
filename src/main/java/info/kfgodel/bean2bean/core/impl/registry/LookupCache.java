package info.kfgodel.bean2bean.core.impl.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * This class implements a simple cache for lookup operations that are only needed the first time
 * Date: 18/02/19 - 23:37
 */
public class LookupCache {

  private Map valuesByKey;

  public static LookupCache create() {
    LookupCache cache = new LookupCache();
    cache.valuesByKey = new HashMap<>(); // This class is a problem for thread safety
    return cache;
  }

  public void invalidate() {
    this.valuesByKey.clear();
  }

  public int size(){
    return valuesByKey.size();
  }

  public <K, V> V retrieveCachedOrProduceFor(K key, Function<K, V> valueGenerator) {
    Optional<V> foundValue = this.retrieveValueFor(key);
    return foundValue
      .orElseGet(()-> {
        V generatedValue = valueGenerator.apply(key);
        this.storeFor(key, generatedValue);
        return generatedValue;
      });
  }

  private <V> Optional<V> retrieveValueFor(Object vector) {
    V value = (V) valuesByKey.get(vector);
    return Optional.ofNullable(value);
  }

  private void storeFor(Object key, Object value) {
    this.valuesByKey.put(key, value);
  }

}
