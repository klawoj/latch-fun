
interface Stager<E extends Enum<E>> {

    void waitUntilStageReached(E stage) throws InterruptedException;

    boolean advanceToTheNextStage();
}
