package obp3.mdb.simple.semantics;

import obp3.mdb.simple.domain.*;
import obp3.sli.core.SemanticRelation;
import obp3.sli.core.operators.product.Product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DebugSemantics<A, C> implements SemanticRelation<DebugAction<A, C>, DebugConfiguration<A, C>> {
    SemanticRelation<A, C> operand;

    public DebugSemantics(SemanticRelation<A, C> operand) {
        this.operand = operand;
    }

    @Override
    public List<DebugConfiguration<A, C>> initial() {
        var initial = operand.initial();
        if (initial.size() <= 1) {
            return initial.stream()
                    .map(Optional::of)
                    .map(o -> new DebugConfiguration<A, C>(o, Optional.empty(), this))
                    .toList();
        }
        return List.of(
                new DebugConfiguration<>(
                        Optional.empty(),
                        Optional.of(new Product<>(new InitAction<>(), operand.initial())),
                        this));
    }

    @Override
    public List<DebugAction<A, C>> actions(DebugConfiguration<A, C> configuration) {
        return configuration.options
                .<List<DebugAction<A, C>>>map(options ->
                        options.r().stream()
                                .map(SelectAction<A, C>::new)
                                .collect(Collectors.toList()))
                .orElseGet(() ->
                        configuration.current.stream()
                                .flatMap(c -> operand.actions(c).stream())
                                .map(StepAction<A, C>::new)
                                .collect(Collectors.toList()));
    }

    @Override
    public List<DebugConfiguration<A, C>> execute(DebugAction<A, C> action, DebugConfiguration<A, C> configuration) {
        if (action instanceof StepAction<A, C> sa) {
            var targets = operand.execute(sa.baseAction, configuration.current.get());
            if (targets.size() <= 1) {
                return targets.stream()
                        .map(Optional::of)
                        .map(o -> new DebugConfiguration<A, C>(o, Optional.empty(), this))
                        .toList();
            }
            return List.of(new DebugConfiguration<>(configuration.current, Optional.of(new Product<>(sa, targets)), this));
        }
        if (action instanceof SelectAction<A, C> sa) {
            return List.of(new DebugConfiguration<>(Optional.of(sa.configuration), Optional.empty(), this));
        }
        return List.of();
    }
}
