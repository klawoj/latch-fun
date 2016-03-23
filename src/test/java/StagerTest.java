import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Arrays;


public class StagerTest extends TestCase {

    enum SampleStagesEnum{
        A,
        B,
        C,
        D,
        E;
    }



    public void testStagerImpl() throws Exception {
        final Stager<SampleStagesEnum> stager = new StagerImpl<>(Arrays.asList(SampleStagesEnum.values()));
        final StringBuffer sb = new StringBuffer();
        Thread a = new ThreadWaitingForState<>(SampleStagesEnum.D,stager,sb);
        Thread b = new ThreadWaitingForState<>(SampleStagesEnum.C,stager,sb);
        Thread c = new ThreadWaitingForState<>(SampleStagesEnum.B,stager,sb);
        Thread d = new ThreadWaitingForState<>(SampleStagesEnum.A,stager,sb);
        a.start();
        b.start();
        c.start();
        d.start();
        stager.waitUntilStageReached(SampleStagesEnum.E);
        Assert.assertEquals(sb.toString(),"ABCD");
        Assert.assertFalse(stager.advanceToTheNextStage());
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