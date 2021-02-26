public class SimpleLinearProbe implements ProbeStrategy {
    public int probe(int arraySize, int originalHash, int attemptNum) {
        return (originalHash + attemptNum) % arraySize; // FIXME to be written
        //return 0; // FIXME remove when ready
    }

}