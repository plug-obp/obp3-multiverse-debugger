package obp3.mdb.simple.domain;

public class StepAction<A, C> extends DebugAction<A, C> {
    public A baseAction;
    public StepAction(A baseAction) {
        this.baseAction = baseAction;
    }

    @Override
    public String toString() {
        return "StepAction{" +
                "baseAction=" + baseAction +
                '}';
    }
}
