package integration.performance;

import org.junit.Test;

/**
 * Created by kfgodel on 29/06/14.
 */
public class MapperPerformanceIT {

    @Test
    public void stateCopying(){
        // Given that I can manually copy state X times in 3 seconds
        // When I do Y bean2bean state mapping in 3 seconds
        // Then Y should be bigger than 0.5X
    }

    @Test
    public void stateTransformation(){
        // Given that I can manually transform state X times in 3 seconds
        // When I do Y bean2bean state transformation in 3 seconds
        // Then Y should be bigger than 0.5X
    }

}
