package algorithm.localsearch.neighborhood;

import algorithm.Box;
import algorithm.Rectangle;
import algorithm.greedy.GreedySolver;
import algorithm.greedy.extender.FirstFitPlacer;
import algorithm.greedy.extender.GreedyExtender;
import algorithm.greedy.ordering.GreedyOrdering;
import algorithm.greedy.ordering.LargestAreaFirst;
import algorithm.greedy.putting.ImprovedBottomLeft;
import algorithm.greedy.putting.PuttingStrategy;
import algorithm.solution.PackingSolution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Geometry implements Neighborhood<PackingSolution> {

    private final GreedySolver<PackingSolution, Rectangle> greedySolver;

    public Geometry() {
        GreedyOrdering<Rectangle> ordering = new LargestAreaFirst();
        PuttingStrategy puttingStrategy = new ImprovedBottomLeft();
        GreedyExtender<PackingSolution, Rectangle> extender = new FirstFitPlacer(puttingStrategy);
        this.greedySolver = new GreedySolver<>(ordering, extender);
    }


    @Override
    public Iterable<PackingSolution> getNeighbors(PackingSolution solution) {
        List<PackingSolution> neighbors = new ArrayList<>();

        PackingSolution clone = solution.copy();
        clone.boxes()
                .sort(Comparator.comparingInt(Box::getUsedArea));

        // Unpack up to 10 boxes with the least used area
        int numBoxesToUnpack = Math.min(10, clone.boxes().size());
        for  (int i = 0; i < numBoxesToUnpack; i++) {
            PackingSolution temp = clone.copy();
            Box box = temp.boxes().get(i);
            List<Rectangle> copiedRectangles = box.getRectangles()
                    .stream()
                    .map(Rectangle::copy)
                    .toList();
            temp.boxes().remove(box);
            PackingSolution baseSolution = temp.copy();
            PackingSolution improvedSolution = greedySolver.solve(baseSolution, copiedRectangles);
            neighbors.add(improvedSolution);
        }

        return neighbors;
    }
}
