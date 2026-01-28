package algorithm.localsearch.objective;

import algorithm.solution.PermutationSolution;

public class PermutationObjective implements Objective<PermutationSolution> {
    @Override
    public double evaluate(PermutationSolution solution) {
        return (new KampkeObjective()).evaluate(solution.decode());
    }
}
