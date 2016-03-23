
interface Stager<E extends Enum<E>> {

    E getCurrentStage();

    void waitUntilStageReached(E stage) throws InterruptedException;

    boolean advanceToTheNextStage();
}
