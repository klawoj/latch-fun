import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.EnumSet;


public class StagerTest extends TestCase {

    enum Stages {
        ZERO,
        A,
        B,
        C,
        D,
        E;
    }


    /**
     * This test checks just the basics;) feel free to add better ones ;)
     * @throws Exception
     */
    public void testStagerImpl() throws Exception {
        final Stager<Stages> stager = new StagerImpl<>(Arrays.asList(Stages.values()));
        final StringBuffer sb = new StringBuffer();

        //start 4 threads
        EnumSet.of(Stages.D, Stages.C, Stages.B, Stages.A).parallelStream().map( stage ->  new ThreadWaitingForState<>(stage,stager,sb)).forEach(Thread::start);
        sb.append("(");
        Assert.assertTrue(stager.advanceToTheNextStage()); //from ZERO to A. that should unblock the threads
        stager.waitUntilStageReached(Stages.E); //Block until other threads complete
        sb.append(")");
        Assert.assertEquals(sb.toString(),"(ABCD)"); //here we are checking the order
        Assert.assertFalse(stager.advanceToTheNextStage()); //no next stage available
    }


    private class ThreadWaitingForState<E extends Enum<E>> extends Thread{

        private final E waitingFor;
        private final Stager<E> stager;
        private StringBuffer log;

        ThreadWaitingForState(E waitingFor, Stager<E> stager, StringBuffer log) {
            this.waitingFor = waitingFor;
            this.stager = stager;
            this.log = log;
        }

        @Override
        public void run() {
            try {
                stager.waitUntilStageReached(waitingFor);
                log.append(waitingFor.name());
                stager.advanceToTheNextStage();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}