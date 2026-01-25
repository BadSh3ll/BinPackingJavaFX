package algorithm.localsearch.objective;

import algorithm.solution.Solution;

public interface Objective<S extends Solution> {
    double evaluate(S solution);
}
