package algorithm.solution;

import algorithm.Box;
import algorithm.Rectangle;
import algorithm.instance.Instance;

import java.util.List;
import java.util.Random;

public class OverlapPackingSolution extends PackingSolution {

    public int currentIteration = 0;
    public int maxIterations;

    public OverlapPackingSolution(List<Box> boxes) {
        super(boxes);
    }

    public OverlapPackingSolution(int boxSize) {
        super(boxSize);
    }

    public static OverlapPackingSolution init(Instance instance, int maxIterations) {
        OverlapPackingSolution solution = new OverlapPackingSolution(instance.boxSize);
        solution.currentIteration = 0;
        solution.maxIterations = maxIterations;

        Random random = new Random();
        int numBoxes = random.nextInt(instance.rectangles.size() - instance.rectangles.size() / 4) + (instance.rectangles.size() / 4);

        for (int i = 0; i < numBoxes; i++) {
            solution.addBox(new Box(instance.boxSize));
        }

        for (Rectangle rectangle : instance.rectangles) {
            int boxIndex = random.nextInt(numBoxes);

            int X = random.nextInt(instance.boxSize - rectangle.getWidth() + 1);
            int Y = random.nextInt(instance.boxSize - rectangle.getHeight() + 1);

            solution.boxes().get(boxIndex).addRectangle(rectangle, X, Y);
        }

        // Clear empty boxes
        solution.boxes().removeIf(box -> box.getRectangles().isEmpty());

        return solution;
    }

    public Box highestOverlapBox() {
        Box highestOverlapBox = null;
        double highestOverlapRate = -1.0;

        for (Box box : boxes()) {
            double overlapRate = box.totalOverlapRate();
            if (overlapRate > highestOverlapRate) {
                highestOverlapRate = overlapRate;
                highestOverlapBox = box;
            }
        }
        return highestOverlapBox;
    }

    public Box leastUsedBox() {
        Box leastUsedBox = null;
        double lowestUtilization = Double.MAX_VALUE;

        for (Box box : boxes()) {
            double utilization = box.getUtilization();
            if (utilization < lowestUtilization) {
                lowestUtilization = utilization;
                leastUsedBox = box;
            }
        }
        return leastUsedBox;
    }

    @Override
    public OverlapPackingSolution copy() {
        PackingSolution newBase = super.copy();
        OverlapPackingSolution newSolution = new OverlapPackingSolution(newBase.boxes());
        newSolution.currentIteration = this.currentIteration;
        newSolution.maxIterations = this.maxIterations;
        return newSolution;
    }
}
