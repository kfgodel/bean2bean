package net.sf.kfgodel.bean2bean.integration.performance;

import org.junit.Test;

/**
 * This class tests the execution performance for manipulator operations
 * Created by kfgodel on 29/06/14.
 */
public class ManipulatorPerformanceIT {

    @Test
    public void getter(){
        //Given that I can execute X manual getter calls in 3 seconds
        // When I execute Y manipulator getter calls in 3 seconds
        // Then Y should be bigger than 0.5X
    }

    @Test
    public void setter(){
        //Given that I can execute X manual setter calls in 3 seconds
        // When I execute Y manipulator setter calls in 3 seconds
        // Then Y should be bigger than 0.5X
    }

    @Test
    public void creator(){
        //Given that I can execute X instantiation calls in 3 seconds
        // When I execute Y manipulator creator calls in 3 seconds
        // Then Y should be bigger than 0.5X--
    }

    @Test
    public void destructor(){
        //Given that I can execute X destructor calls in 3 seconds
        // When I execute Y manipulator destructor calls in 3 seconds
        // Then Y should be bigger than 0.5X--
    }

}
