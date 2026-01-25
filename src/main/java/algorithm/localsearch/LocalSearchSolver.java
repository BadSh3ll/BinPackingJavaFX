package algorithm.localsearch;

import algorithm.localsearch.neighborhood.Neighborhood;
import algorithm.localsearch.objective.Objective;
import algorithm.solution.Solution;

public class LocalSearchSolver<S extends Solution> {

    private final Neighborhood<S> neighborhood;
    private final Objective<S> objective;
    private final int maxIterations;

    public LocalSearchSolver(Neighborhood<S> neighborhood, Objective<S> objective, int maxIterations) {
        this.neighborhood = neighborhood;
        this.objective = objective;
        this.maxIterations = maxIterations;
    }

    public S solve(S initial) {
        S current = initial;
        double currentScore = objective.evaluate(current);

        for (int i = 0; i < maxIterations; i++) {
            S bestNeighbor = null;
            double bestScore = currentScore;
            for (S neighbor : neighborhood.getNeighbors(current)) {
                double neighborScore = objective.evaluate(neighbor);
                if (neighborScore > bestScore) {
                    bestScore = neighborScore;
                    bestNeighbor = neighbor;
                }
            }

            if (bestNeighbor == null) {
                break; // No better neighbor found
            }

            current = bestNeighbor;
            currentScore = bestScore;
        }
        return current;
    }


}
