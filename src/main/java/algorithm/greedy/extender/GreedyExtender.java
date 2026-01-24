package algorithm.greedy.extender;

import algorithm.Element;
import algorithm.solution.Solution;

public interface GreedyExtender<S extends Solution, E extends Element> {
    S extend(S initial, E element);
}
