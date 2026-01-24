package algorithm.instance;

import algorithm.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class InstanceGenerator {

    public static Instance generate(InstanceParams params) {
        List<Rectangle> rectangles = new ArrayList<>();

        for (int i = 0; i < params.getNumRectangles(); i++) {
            int width = ThreadLocalRandom.current().nextInt(
                params.getMinWidth(),
                params.getMaxWidth() + 1
            );
            int height = ThreadLocalRandom.current().nextInt(
                params.getMinHeight(),
                params.getMaxHeight() + 1
            );
            rectangles.add(new Rectangle(width, height));
        }

        return new Instance(params.getBoxSize(), rectangles);
    }
}
