package obp3.mdb.simple.domain;

public class SelectAction<A, C> extends DebugAction<A, C> {
    public C configuration;
    public SelectAction(C configuration) {
        this.configuration = configuration;
    }

    @Override
    public String toString() {
        return "SelectAction{" +
                "configuration=" + configuration +
                '}';
    }
}
