package net.sf.kfgodel.bean2bean.integration.functional.manipulator.accessors;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class verifies that bean2bean behaves correctly when null instances are found on accessors
 * Created by kfgodel on 06/07/14.
 */
@RunWith(HierarchicalContextRunner.class)
public class NullInPropertySequenceIT {

    public class FoundBeforeLastProperty {

        public class WhenProducing{

            @Test
            public void produceNull(){

            }

            @Test
            public void createMissingObjects(){

            }

            @Test
            public void throwException(){

            }

        }

        public class WhenConsuming{

            @Test
            public void stopConsumption(){

            }

            @Test
            public void createMissingObjects(){

            }

            @Test
            public void throwException(){

            }
        }
    }

    public class FoundOnLastProperty {

        public class WhenProducing{

            @Test
            public void produceNull(){

            }

            @Test
            public void createObject(){

            }

            @Test
            public void throwException(){

            }

        }

        public class WhenConsuming{

            @Test
            public void replaceWithValue(){

            }

        }

    }

}
