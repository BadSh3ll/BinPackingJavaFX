package algorithm.localsearch.objective;

import algorithm.solution.PackingSolution;

public class MinimizeBinObjective implements Objective<PackingSolution> {

    @Override
    public double evaluate(PackingSolution solution) {
        return - solution.boxes().size();
    }
}

