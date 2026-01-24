package algorithm.greedy.ordering;

import java.util.List;

public interface GreedyOrdering<E> {
    List<E> order(List<E> elements);
}
