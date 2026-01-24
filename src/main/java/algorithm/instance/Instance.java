package algorithm.instance;

import algorithm.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Instance {

    public int boxSize;
    public List<Rectangle> rectangles;

    public Instance(int boxSize) {
        this.boxSize = boxSize;
        this.rectangles = new ArrayList<>();
    }

    public Instance(int boxSize, List<Rectangle> rectangles) {
        this.boxSize = boxSize;
        this.rectangles = rectangles;
    }
}
