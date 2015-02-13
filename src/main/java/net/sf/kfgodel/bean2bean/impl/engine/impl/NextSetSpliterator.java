package net.sf.kfgodel.bean2bean.impl.engine.impl;

import ar.com.kfgodel.diamond.api.Diamond;
import ar.com.kfgodel.diamond.api.types.TypeInstance;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * This type represents the iterator of type implied sets
 * Created by kfgodel on 13/02/15.
 */
public class NextSetSpliterator implements Spliterator<Object> {
    
    private boolean firstTime;
    private Object initialObject;
    private Iterator<TypeInstance> nextSets;
    
    @Override
    public boolean tryAdvance(Consumer<? super Object> action) {
        if(firstTime){
            firstTime = false;
            //Optimization to avoid extra steps the first time
            action.accept(initialObject);
            return true;
        }
        if(nextSets == null){
            // The second time
            nextSets = Diamond.of(initialObject.getClass()).inheritance().typeLineage().allRelatedTypes().iterator();
        }
        boolean hasNext = nextSets.hasNext();
        if(hasNext){
            TypeInstance typeInstance = nextSets.next();
            action.accept(typeInstance);
        }
        return hasNext;
    }

    @Override
    public Spliterator<Object> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return Spliterator.DISTINCT & Spliterator.IMMUTABLE & Spliterator.NONNULL & Spliterator.ORDERED;
    }

    public static NextSetSpliterator create(Object initialObject) {
        NextSetSpliterator spliterator = new NextSetSpliterator();
        spliterator.initialObject = initialObject;
        spliterator.firstTime = true;
        return spliterator;
    }

}
