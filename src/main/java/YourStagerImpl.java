import java.util.List;

/**
 * This class allows us to wait in a thread until a desired state is reached using <code>waitUntilStageReached(E stage)</code>
 * List of possible states is modelled by a list of Enum values that is passed to the constructor
 * The list must be not null and have at least one element.
 * The first element of the list is the initial state.
 * The last element of the list is the last state that can be reached
 * States can only change one way, in the order of the list. This is done with <code>advanceToTheNextStage()</code> method
 * The class is thread safe!
 * @param <E>
 */
class YourStagerImpl<E extends Enum<E>> implements Stager<E>  {

    public YourStagerImpl(List<E> states) {
        //TODO
    }

    /**
     * Thread invoking this method should block until the stage is reached, then it should return
     * If the stage has been already reached, or if we have some later stage, that method should return immediately
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    @Override
    public void waitUntilStageReached(E stage) throws InterruptedException {
        //TODO
    }

    /**
     * Changes the state to the next one if there is a next state
     * @return true if there was a next state, false otherwise
     */
    @Override
    public boolean advanceToTheNextStage() {
        //TODO
        return false;
    }
}
