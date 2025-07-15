package obp3.mdb.simple.domain;

import obp3.mdb.simple.semantics.DebugSemantics;
import obp3.sli.core.operators.product.Product;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DebugConfiguration<A, C> {
    public Optional<C> current;
    public Optional<Product<DebugAction<A, C>, List<C>>> options;
    public DebugSemantics<A, C> semantics;

    public DebugConfiguration(
            Optional<C> current,
            Optional<Product<DebugAction<A, C>, List<C>>> options,
            DebugSemantics<A, C> semantics) {
        this.current = current;
        this.options = options;
        this.semantics = semantics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DebugConfiguration<?, ?> that)) return false;
        return Objects.equals(current, that.current) && Objects.equals(options, that.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(current, options);
    }
}
