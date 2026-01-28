package algorithm.solution;

import algorithm.Rectangle;
import algorithm.greedy.GreedySolver;
import algorithm.greedy.extender.FirstFitPlacer;
import algorithm.greedy.extender.GreedyExtender;
import algorithm.greedy.ordering.GreedyOrdering;
import algorithm.greedy.ordering.NoOrdering;
import algorithm.greedy.putting.BottemLeft;
import algorithm.greedy.putting.PuttingStrategy;

import java.util.ArrayList;
import java.util.List;

public class PermutationSolution implements Solution {


    public List<Rectangle> current;
    private final int boxSize;

    static GreedySolver<PackingSolution, Rectangle> greedySolver;


    public  List<Rectangle> getCurrent() {
        return current;
    }

    public PermutationSolution(List<Rectangle> initial, int boxSize) {
        if (greedySolver == null) {
            GreedyOrdering<Rectangle> ordering = new NoOrdering();
            PuttingStrategy putting = new BottemLeft();
            GreedyExtender<PackingSolution, Rectangle> extender = new FirstFitPlacer(putting);
            greedySolver = new GreedySolver<>(ordering, extender);
        }
        this.current = initial;
        this.boxSize = boxSize;
    }

    public PackingSolution decode() {
        PackingSolution initial = new PackingSolution(boxSize);
        return greedySolver.solve(initial, current);
    }

    @Override
    public PermutationSolution copy() {
        List<Rectangle> permutations = new ArrayList<>();
        for (Rectangle rect : current) {
            permutations.add(rect.copy());
        }
        return new PermutationSolution(permutations, boxSize);
    }
}
