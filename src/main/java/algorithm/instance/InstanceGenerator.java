package algorithm.instance;

import algorithm.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class InstanceGenerator {

    public static Instance generate(InstanceParams params) {
        List<Rectangle> rectangles = new ArrayList<>();

        for (int i = 0; i < params.numRectangles(); i++) {
            int width = ThreadLocalRandom.current().nextInt(
                params.minWidth(),
                params.maxWidth() + 1
            );
            int height = ThreadLocalRandom.current().nextInt(
                params.minHeight(),
                params.maxHeight() + 1
            );
            rectangles.add(new Rectangle(width, height));
        }

        return new Instance(params.boxSize(), rectangles);
    }
}
