package algorithm.greedy;

import algorithm.Element;
import algorithm.greedy.extender.GreedyExtender;
import algorithm.greedy.ordering.GreedyOrdering;
import algorithm.solution.Solution;
import java.util.List;

public class GreedySolver<S extends Solution, E extends Element> {

    private final GreedyOrdering<E> ordering;
    private final GreedyExtender<S, E> extender;

    public GreedySolver(
        GreedyOrdering<E> ordering,
        GreedyExtender<S, E> extender
    ) {
        this.ordering = ordering;
        this.extender = extender;
    }

    public S solve(S initial, List<E> elements) {
        List<E> ordered = ordering.order(elements);
        S current = initial;
        for (E element : ordered) {
            current = extender.extend(current, element);
        }
        return current;
    }
}
