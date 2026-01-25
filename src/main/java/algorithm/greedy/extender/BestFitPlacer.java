package algorithm.greedy.extender;

import algorithm.Box;
import algorithm.Rectangle;
import algorithm.greedy.putting.PuttingStrategy;
import algorithm.greedy.putting.TryPutResult;
import algorithm.solution.PackingSolution;

public class BestFitPlacer
    implements GreedyExtender<PackingSolution, Rectangle>
{

    private final PuttingStrategy putting;

    public BestFitPlacer(PuttingStrategy putting) {
        this.putting = putting;
    }

    @Override
    public PackingSolution extend(
        PackingSolution solution,
        Rectangle rectangle
    ) {
        // Try existing boxes to find the tightest fit
        int bestArea = Integer.MAX_VALUE;
        Box bestBox = null;
        TryPutResult bestPosition = null;

        for (Box box : solution.boxes()) {
            TryPutResult result = putting.tryPut(rectangle, box);
            if (result != null) {
                int area = box.getArea() - rectangle.getArea() - box.getUsedArea();
                if (area < bestArea) {
                    bestArea = area;
                    bestBox = box;
                    bestPosition = result;
                }
            }
        }

        if (bestBox != null) {
            bestBox.addRectangle(
                bestPosition.rotated() ? rectangle.rotate() : rectangle,
                bestPosition.x(),
                bestPosition.y()
            );
            return solution;
        }

        // Create a new box if no existing box can fit the rectangle
        Box newBox = new Box(solution.boxes().getFirst().getSize());
        newBox.addRectangle(rectangle, 0, 0);

        solution.addBox(newBox);
        return solution;
    }
}
