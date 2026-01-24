package algorithm.greedy.extender;

import algorithm.Box;
import algorithm.Rectangle;
import algorithm.greedy.putting.PuttingStrategy;
import algorithm.greedy.putting.TryPutResult;
import algorithm.solution.PackingSolution;

public class FirstFitPlacer
    implements GreedyExtender<PackingSolution, Rectangle>
{

    private final PuttingStrategy putting;

    public FirstFitPlacer(PuttingStrategy putting) {
        this.putting = putting;
    }

    @Override
    public PackingSolution extend(
        PackingSolution solution,
        Rectangle rectangle
    ) {
        // Try existing boxes
        for (Box box : solution.boxes()) {
            TryPutResult result = this.putting.tryPut(rectangle, box);
            if (result != null) {
                box.addRectangle(
                    result.rotated() ? rectangle.rotate() : rectangle,
                    result.x(),
                    result.y()
                );
                return solution;
            }
        }

        // Create new box if no existing box can fit the rectangle
        Box newBox = new Box(solution.boxes().getFirst().getSize());
        newBox.addRectangle(rectangle, 0, 0);

        // Add new box to solution
        solution.addBox(newBox);
        return solution;
    }
}
