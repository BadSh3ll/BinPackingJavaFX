package algorithm.greedy.putting;

import algorithm.Box;
import algorithm.Rectangle;

public class ImprovedBottomLeft implements PuttingStrategy {

    private int MaxDownY(Rectangle rectangle, Box box) {
        int minY = 0;
        for (Rectangle placedRect : box.getRectangles()) {
            if (
                rectangle.getX() < placedRect.getX() + placedRect.getWidth() &&
                rectangle.getX() + rectangle.getWidth() > placedRect.getX()
            ) {
                // Rectangles overlap horizontally (in x-axis)
                // This placed rectangle blocks downward movement
                minY = Math.max(minY, placedRect.getY() + placedRect.getHeight());
            }
        }

        return minY;
    }

    private int MaxLeftX(Rectangle rectangle, Box box) {
        int minX = 0; // Left edge of the box
        for (Rectangle placedRect : box.getRectangles()) {
            if (
                rectangle.getY() < placedRect.getY() + placedRect.getHeight() &&
                rectangle.getY() + rectangle.getHeight() > placedRect.getY()
            ) {
                // Rectangles overlap vertically (in y-axis)
                // This placed rectangle blocks leftward movement
                minX = Math.max(minX, placedRect.getX() + placedRect.getWidth());
            }
        }
        return minX;
    }


    @Override
    public TryPutResult tryPut(Rectangle rectangle, Box box) {

        Boolean[] orientations = new Boolean[] { false, true };

        for (Boolean rotated : orientations) {

            Rectangle temp = rotated ?
                    new Rectangle(rectangle.getHeight(), rectangle.getWidth()) :
                    new Rectangle(rectangle.getWidth(), rectangle.getHeight());

            temp.setPosition(box.getSize() - temp.getWidth(), box.getSize() - temp.getHeight());

            boolean moved;
            do {
                moved = false;
                int minY = MaxDownY(temp, box);
                if (minY < temp.getY()) {
                    temp.setPosition(temp.getX(), minY);
                    moved = true;
                }

                int minX = MaxLeftX(temp, box);
                if (minX < temp.getX()) {
                    temp.setPosition(minX, temp.getY());
                    moved = true;
                }
            } while (moved);
            if (!box.isOverflow(temp) && !box.isOverlapping(temp)) {
                return new TryPutResult(temp.getX(), temp.getY(), rotated);
            }
        }
        return TryPutResult.fail();
    }
}
