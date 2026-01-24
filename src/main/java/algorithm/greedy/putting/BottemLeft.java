package algorithm.greedy.putting;

import algorithm.Box;
import algorithm.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class BottemLeft implements PuttingStrategy {

    private class Candidate {

        private final int x;
        private final int y;

        public Candidate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    @Override
    public TryPutResult tryPut(Rectangle rectangle, Box box) {
        List<Candidate> candidates = new ArrayList<>();
        // First candidate is the origin
        candidates.add(new Candidate(0, 0));

        // Then sides of placed rectangles
        for (Rectangle rect : box.getRectangles()) {
            candidates.add(
                new Candidate(rect.getX() + rect.getWidth(), rect.getY())
            );
            candidates.add(
                new Candidate(rect.getX(), rect.getY() + rect.getHeight())
            );
        }

        // Sort by y-coordinate, then by x-coordinate (bottom-left)
        candidates.sort((c1, c2) -> {
            int x1 = c1.getX();
            int y1 = c1.getY();
            int x2 = c2.getX();
            int y2 = c2.getY();
            if (y1 == y2) {
                return x1 - x2;
            }
            return y1 - y2;
        });

        Boolean[] orientations = new Boolean[] { false, true };

        for (Boolean rotated : orientations) {
            for (Candidate candidate : candidates) {
                Rectangle testRect = rotated
                    ? new Rectangle(rectangle.getHeight(), rectangle.getWidth())
                    : new Rectangle(
                          rectangle.getWidth(),
                          rectangle.getHeight(),
                          rotated
                      );

                testRect.setPosition(candidate.getX(), candidate.getY());

                boolean isOverflow = box.isOverflow(testRect);
                boolean isOverlapping = box.isOverlapping(testRect);

                if (!isOverflow && !isOverlapping) {
                    return new TryPutResult(
                        candidate.getX(),
                        candidate.getY(),
                        rotated
                    );
                }
            }
        }

        return null;
    }
}
