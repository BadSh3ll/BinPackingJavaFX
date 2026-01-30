package algorithm.localsearch.neighborhood;

import algorithm.Box;
import algorithm.Rectangle;
import algorithm.solution.OverlapPackingSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


// ** Allow partial overlaps **:
// The geometry-based neighborhood is adjusted to the situation that
// rectangles are allowed to overlap by a certain percentage.
// The overlap of two rectangles is defined as the shared area divided by the maximum of the two rectangle areas.
// This percentage is initially 100 (so that an optimal solution is easy to find).
// Over time, the percentage decreases, and violations are strictly penalized in the objective function.
// In the end, of course, you need to ensure that a guaranteed non-overlapping solution is ultimately achieved.

public class Overlap implements Neighborhood<OverlapPackingSolution> {



    @Override
    public Iterable<OverlapPackingSolution> getNeighbors(OverlapPackingSolution solution) {

        List<OverlapPackingSolution> neighbors = new ArrayList<>();

        OverlapPackingSolution temp = solution.copy();
        temp.currentIteration += 1;


        Random random = new Random();

        int boxIndex = random.nextInt(solution.boxes().size());
        int rectIndex = random.nextInt(solution.boxes().get(boxIndex).getRectangles().size());

        // 1. Move rectangle to a different position in the same box

        OverlapPackingSolution neighbor1 = temp.copy();
        Box box = neighbor1.boxes().get(boxIndex);
        Rectangle rectangle = box.getRectangles().get(rectIndex);

        int newX = random.nextInt(box.getSize() - rectangle.getWidth() + 1);
        int newY = random.nextInt(box.getSize() - rectangle.getHeight() + 1);

        rectangle.setPosition(newX, newY);
        neighbors.add(neighbor1);

        // 2. Move rectangle to a different box
        if (solution.boxes().size() > 1) {
            OverlapPackingSolution neighbor2 = temp.copy();
            Box boxFrom = neighbor2.boxes().get(boxIndex);
            Rectangle rectangle2 = boxFrom.getRectangles().get(rectIndex);

            // Select a different box
            int boxIndexTo;
            do {
                boxIndexTo = random.nextInt(solution.boxes().size());
            } while (boxIndexTo == boxIndex);

            Box boxTo = neighbor2.boxes().get(boxIndexTo);

            // Remove from current box
            boxFrom.getRectangles().remove(rectangle2);

            // Add to new box at random position
            int newX2 = random.nextInt(boxTo.getSize() - rectangle2.getWidth() + 1);
            int newY2 = random.nextInt(boxTo.getSize() - rectangle2.getHeight() + 1);
            boxTo.addRectangle(rectangle2, newX2, newY2);

            neighbors.add(neighbor2);
        }

        // 3. Swap rectangles between two boxes
        if (solution.boxes().size() > 1) {
            OverlapPackingSolution neighbor3 = temp.copy();
            Box boxA = neighbor3.boxes().get(boxIndex);
            Rectangle rectangleA = boxA.getRectangles().get(rectIndex);
            // Select a different box
            int boxIndexB;
            do {
                boxIndexB = random.nextInt(solution.boxes().size());
            } while (boxIndexB == boxIndex);

            Box boxB = neighbor3.boxes().get(boxIndexB);
            if (!boxB.getRectangles().isEmpty()) {
                int rectIndexB = random.nextInt(boxB.getRectangles().size());
                Rectangle rectangleB = boxB.getRectangles().get(rectIndexB);
                // Swap rectangles
                boxA.getRectangles().remove(rectangleA);
                boxB.getRectangles().remove(rectangleB);
                // Add rectangleA to boxB at random position
                int newX_A = random.nextInt(boxB.getSize() - rectangleA.getWidth() + 1);
                int newY_A = random.nextInt(boxB.getSize() - rectangleA.getHeight() + 1);
                boxB.addRectangle(rectangleA, newX_A, newY_A);
                // Add rectangleB to boxA at random position
                int newX_B = random.nextInt(boxA.getSize() - rectangleB.getWidth() + 1);
                int newY_B = random.nextInt(boxA.getSize() - rectangleB.getHeight() + 1);
                boxA.addRectangle(rectangleB, newX_B, newY_B);
                neighbors.add(neighbor3);
            }
        }

        // 4. Create a new box and move a rectangle into it
        OverlapPackingSolution neighbor4 = temp.copy();
        Box boxFrom = neighbor4.boxes().get(boxIndex);
        Rectangle rectangle4 = boxFrom.getRectangles().get(rectIndex);
        // Remove from current box
        boxFrom.getRectangles().remove(rectangle4);
        // Create new box
        Box newBox = new Box(boxFrom.getSize());
        // Add rectangle to new box at random position
        int newX4 = random.nextInt(newBox.getSize() - rectangle4.getWidth() + 1);
        int newY4 = random.nextInt(newBox.getSize() - rectangle4.getHeight() + 1);
        newBox.addRectangle(rectangle4, newX4, newY4);
        neighbor4.addBox(newBox);
        neighbors.add(neighbor4);

        return neighbors;

    }
}
