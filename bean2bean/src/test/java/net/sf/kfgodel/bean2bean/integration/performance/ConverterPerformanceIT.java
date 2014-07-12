package net.sf.kfgodel.bean2bean.integration.performance;

import org.junit.Test;

/**
 * Created by kfgodel on 29/06/14.
 */
public class ConverterPerformanceIT {

    @Test
    public void numberCasting(){
        // Given that I can manually cast an int toInstanceOf long X times in 3 seconds
        // When I do Y bean2bean int toInstanceOf long conversion in 3 seconds
        // Then Y should be bigger than 0.5X
    }

    @Test
    public void stringToNumber(){
        // Given that I can manually parse a string toInstanceOf long X times in 3 seconds
        // When I do Y bean2bean string toInstanceOf long conversion in 3 seconds
        // Then Y should be bigger than 0.5X
    }

    @Test
    public void objectToJson(){
        // Given that I can manually convert an object toInstanceOf a json string X times in 3 seconds
        // When I do Y bean2bean object toInstanceOf json string conversion in 3 seconds
        // Then Y should be bigger than 0.5X
    }

    @Test
    public void arrayToList(){
        // Given that I can manually convert an array of objects toInstanceOf an arraylist X times in 3 seconds
        // When I do Y bean2bean array toInstanceOf arraylist conversion in 3 seconds
        // Then Y should be bigger than 0.5X
    }

    @Test
    public void objectToDto(){
        // Given that I can manually convert an object toInstanceOf a dto object X times in 3 seconds
        // When I do Y bean2bean object toInstanceOf dto conversion in 3 seconds
        // Then Y should be bigger than 0.5X
    }



}
