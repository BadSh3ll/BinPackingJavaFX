package algorithm.solution;

import algorithm.Box;
import java.util.ArrayList;
import java.util.List;

public record PackingSolution(List<Box> boxes) implements Solution {

    public PackingSolution(int boxSize) {
        this(new ArrayList<>());
        this.boxes.add(new Box(boxSize));
    }

    public void addBox(Box box) {
        boxes.add(box);
    }
}
