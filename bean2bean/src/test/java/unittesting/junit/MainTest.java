package unittesting.junit;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import org.junit.*;
import org.junit.runner.RunWith;

@RunWith(HierarchicalContextRunner.class)
public class MainTest {
    //  some constants and helpers 

    @BeforeClass
    public static void runsOnlyOnce_Before_AllTestsInMainTest() throws Exception {
    }

    @AfterClass
    public static void runsOnlyOnce_After_AllTestsInMainTest() throws Exception {
    }

    @Test
    public void simpleTestInTheMainContext() throws Exception {
    }

    @Test
    public void anotherTestInTheMainContext() throws Exception {
    }

    //  more tests in the main context 

    public class SubContext {
        @Before
        public void runs_Before_EachTestWithinSubContextAndAllContainingContexts() {
        }

        @After
        public void runs_After_EachTestWithinSubContextAndAllContainingContexts() {
        }

        @Test
        public void simpleTestInTheSubContext() throws Exception {
        }

        //  more tests in the sub context 

        public class SubSubContext1 {
            @Before
            public void runs_Before_EachTestWithinSubSubContext1AndAllContainingContexts() {
            }

            @After
            public void runs_After_EachTestWithinSubSubContext1AndAllContainingContexts() {
            }

            @Test
            public void simpleTestInTheSubSubContext1() throws Exception {
            }

            //  more tests in the sub sub context 1 
        }

        public class SubSubContext2 {
            @Before
            public void runs_Before_EachTestWithinSubSubContext2AndAllContainingContexts() {
            }

            @After
            public void runs_After_EachTestWithinSubSubContext2AndAllContainingContexts() {
            }

            @Test
            public void simpleTestInTheSubSubContext2() throws Exception {
            }

            //  more tests in the sub sub context 2 
        }
    }
}