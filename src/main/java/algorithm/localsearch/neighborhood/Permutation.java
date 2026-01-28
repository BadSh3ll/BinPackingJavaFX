package algorithm.localsearch.neighborhood;

import algorithm.Rectangle;
import algorithm.solution.PermutationSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Permutation implements Neighborhood<PermutationSolution> {

    @Override
    public Iterable<PermutationSolution> getNeighbors(PermutationSolution initial) {

        List<PermutationSolution> neighbors = new ArrayList<>();

        PermutationSolution temp = initial.copy();

        Random random = new Random();
        int chosenIndex = random.nextInt(temp.getCurrent().size());
        int swapIndex;
        do {
            swapIndex = random.nextInt(temp.getCurrent().size());
        } while (chosenIndex == swapIndex);

        // Swap the 2 rectangles
        Rectangle rectangle = temp.getCurrent().get(swapIndex);
        temp.getCurrent().set(swapIndex, temp.getCurrent().get(chosenIndex));
        temp.getCurrent().set(chosenIndex, rectangle);

        neighbors.add(temp);
        return neighbors;

    }
}
