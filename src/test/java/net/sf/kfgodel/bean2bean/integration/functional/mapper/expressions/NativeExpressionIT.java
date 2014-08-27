package net.sf.kfgodel.bean2bean.integration.functional.mapper.expressions;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import net.sf.kfgodel.bean2bean.api.expressions.ConsumerExpressionContext;
import net.sf.kfgodel.bean2bean.impl.B2bApiImpl;
import net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.UnannotatedTestDestination;
import net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.UnannotatedTestSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

/**
 * This type test the native expressions that allows reflection mappings
 * Created by kfgodel on 06/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class NativeExpressionIT extends JavaSpec<B2bContext> {
    @Override
    public void define() {

        beforeEach(()->{
            context().b2b(() -> B2bApiImpl.create());
        });


        it("can be used as getter", ()->{
            // Given
            context().b2b().map().fromInstanceOf(UnannotatedTestSource.class)
                    .toInstanceOf(UnannotatedTestDestination.class).instructedAs((mappingContext) -> {
                mappingContext.fromExpression((producerContext) -> "Totally custom").toProperty("destinationProperty");
            });

            //When
            UnannotatedTestSource sourceObject = UnannotatedTestSource.create("Hello");
            UnannotatedTestDestination destination = context().b2b().convert().from(sourceObject).toInstanceOf(UnannotatedTestDestination.class);

            //Then
            assertThat(destination.getDestinationProperty()).isEqualTo("Totally custom");
        });

        it("can be used as setter", ()-> {
            // Given
            Consumer<ConsumerExpressionContext> mockedConsumer = mock(Consumer.class);
            doAnswer(new Answer() {
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    ConsumerExpressionContext consumerContext = (ConsumerExpressionContext) invocation.getArguments()[0];
                    assertThat(consumerContext.<String>getDestinationValue()).isEqualTo("Hello");
                    return null;
                }
            }).when(mockedConsumer).accept(any());

            context().b2b().map().fromInstanceOf(UnannotatedTestSource.class)
                    .toInstanceOf(UnannotatedTestDestination.class).instructedAs((mappingContext) -> {
                mappingContext.fromProperty("sourceProperty").toExpression(mockedConsumer);
            });

            //When
            UnannotatedTestSource sourceObject = UnannotatedTestSource.create("Hello");
            UnannotatedTestDestination destination = context().b2b().convert().from(sourceObject).toInstanceOf(UnannotatedTestDestination.class);

            //Then
            verify(mockedConsumer).accept(anyObject());
        });

        it("can be used as transformer", ()->{
            // Given
            context().b2b().map().fromInstanceOf(UnannotatedTestSource.class)
                    .toInstanceOf(UnannotatedTestDestination.class).instructedAs((mappingContext) -> {
                mappingContext
                        .fromProperty("sourceProperty")
                        .transformedWithExpression((transformerContext) -> "Bye")
                        .toProperty("destinationProperty");
            });

            //When
            UnannotatedTestSource sourceObject = UnannotatedTestSource.create("Hello");
            UnannotatedTestDestination destination = context().b2b().convert().from(sourceObject).toInstanceOf(UnannotatedTestDestination.class);

            //Then
            assertThat(destination.getDestinationProperty()).isEqualTo("Bye");
        });

        it("can be used as complete mapping operation", ()->{
            // Given
            context().b2b().map().fromInstanceOf(UnannotatedTestSource.class)
                    .toInstanceOf(UnannotatedTestDestination.class).using((mappingContext) -> {
                UnannotatedTestSource source = mappingContext.getSourceObject();
                UnannotatedTestDestination destination = mappingContext.getDestinationObject();

                String sourcePropertyValue = source.getSourceProperty();
                destination.setDestinationProperty(sourcePropertyValue);
            });

            //When
            UnannotatedTestSource sourceObject = UnannotatedTestSource.create("Hello");
            UnannotatedTestDestination destination = context().b2b().convert().from(sourceObject).toInstanceOf(UnannotatedTestDestination.class);

            //Then
            assertThat(destination.getDestinationProperty()).isEqualTo("Hello");
        });
    }
}
