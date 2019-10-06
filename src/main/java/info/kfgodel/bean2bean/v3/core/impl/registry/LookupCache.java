package info.kfgodel.bean2bean.v3.core.impl.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * This class implements a simple cache for lookup operations that are only needed the first time
 * Date: 18/02/19 - 23:37
 */
public class LookupCache {

  private Map valuesByKey;

  public static LookupCache create() {
    LookupCache cache = new LookupCache();
    cache.valuesByKey = new ConcurrentHashMap<>();
    return cache;
  }

  public void invalidate() {
    this.valuesByKey.clear();
  }

  public int size(){
    return valuesByKey.size();
  }

  public <K, V> V retrieveCachedOrProduceFor(K key, Function<K, V> valueGenerator) {
    return (V) this.valuesByKey.computeIfAbsent(key, valueGenerator);
  }

}
