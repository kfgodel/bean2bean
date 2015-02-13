package net.sf.kfgodel.bean2bean.unit;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.Lists;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import net.sf.kfgodel.bean2bean.impl.engine.impl.NextSetSpliterator;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static net.sf.kfgodel.bean2bean.assertions.B2bAssertions.assertThat;

/**
 * This type verifies the correct iteration order for the set iterator
 * Created by kfgodel on 13/02/15.
 */
@RunWith(JavaSpecRunner.class)
public class NextSetIteratorTest extends JavaSpec<B2bTestContext> {
    @Override
    public void define() {
        describe("the next set iterator", () -> {
            
            it("starts from an object",()->{
                ArrayList<String> initialObject = new ArrayList<String>();

                Iterator<Object> iterator = Spliterators.iterator(NextSetSpliterator.create(initialObject));

                assertThat(iterator.hasNext()).isTrue();
                assertThat(iterator.next()).isSameAs(initialObject);
            });

            /**
             * A type implies a set of objects, the iteration gos from smaller to bigger set
             */
            it("iterates all types of the object from most specific to less specific",()->{
                ArrayList<String> initialObject = new ArrayList<String>();

                List<String> iterated = StreamSupport.stream(NextSetSpliterator.create(initialObject), false)
                        .map(Object::toString)
                        .collect(Collectors.toList());

                assertThat(iterated).isEqualTo(Lists.newArrayList(
                        "[]", 
                        "java.util.ArrayList", 
                        "java.util.AbstractList<E extends java.lang.Object>", 
                        "java.util.AbstractList", 
                        "java.util.List<E extends java.lang.Object>", 
                        "java.util.List", "java.util.RandomAccess", 
                        "java.lang.Cloneable", 
                        "java.io.Serializable", 
                        "java.util.AbstractCollection<E extends java.lang.Object>", 
                        "java.util.AbstractCollection", 
                        "java.util.Collection<E extends java.lang.Object>", 
                        "java.util.Collection", 
                        "java.lang.Object", 
                        "java.lang.Iterable<E extends java.lang.Object>", 
                        "java.lang.Iterable"));
            });
        });


    }
}