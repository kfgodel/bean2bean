package net.sf.kfgodel.bean2bean.integration.performance;

import org.junit.Test;

/**
 * Created by kfgodel on 29/06/14.
 */
public class ConverterPerformanceIT {

    @Test
    public void numberCasting(){
        // Given that I can manually cast an int to long X times in 3 seconds
        // When I do Y bean2bean int to long conversion in 3 seconds
        // Then Y should be bigger than 0.5X
    }

    @Test
    public void stringToNumber(){
        // Given that I can manually parse a string to long X times in 3 seconds
        // When I do Y bean2bean string to long conversion in 3 seconds
        // Then Y should be bigger than 0.5X
    }

    @Test
    public void objectToJson(){
        // Given that I can manually convert an object to a json string X times in 3 seconds
        // When I do Y bean2bean object to json string conversion in 3 seconds
        // Then Y should be bigger than 0.5X
    }

    @Test
    public void arrayToList(){
        // Given that I can manually convert an array of objects to an arraylist X times in 3 seconds
        // When I do Y bean2bean array to arraylist conversion in 3 seconds
        // Then Y should be bigger than 0.5X
    }

    @Test
    public void objectToDto(){
        // Given that I can manually convert an object to a dto object X times in 3 seconds
        // When I do Y bean2bean object to dto conversion in 3 seconds
        // Then Y should be bigger than 0.5X
    }



}
