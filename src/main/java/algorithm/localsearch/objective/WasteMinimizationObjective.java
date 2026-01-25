package algorithm.localsearch.objective;

import algorithm.Box;
import algorithm.solution.PackingSolution;

import java.util.List;

public class WasteMinimizationObjective implements Objective<PackingSolution> {

    @Override
    public double evaluate(PackingSolution solution) {
        List<Box> boxes = solution.boxes();
        int totalBoxArea = boxes.size() * boxes.getFirst().getArea();
        int totalUsedArea = boxes.stream().mapToInt(Box::getUsedArea).sum();

        double wasteRatio = (double) (totalBoxArea - totalUsedArea) / totalBoxArea;
        double binPenalty = boxes.size() * 100; // Heavy penalty for more bins

        return - (wasteRatio + binPenalty); // Lower is better
    }
}