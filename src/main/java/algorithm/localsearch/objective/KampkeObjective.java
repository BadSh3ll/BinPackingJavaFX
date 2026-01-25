package algorithm.localsearch.objective;

import algorithm.Box;
import algorithm.solution.PackingSolution;

import java.util.List;

public class KampkeObjective implements Objective<PackingSolution> {

    @Override
    public double evaluate(PackingSolution solution) {
        List<Box> boxes = solution.boxes();
        long totalUsedArea = 0;
        for (Box box : boxes) {
            totalUsedArea += (int) Math.pow(box.getUtilization(), 2);
        }
        return (double) totalUsedArea / boxes.size();
    }
}