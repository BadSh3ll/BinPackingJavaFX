package algorithm;

import java.util.ArrayList;
import java.util.List;

public class Box {

    private final int size;
    private final int area;
    private final List<Rectangle> rectangles;

    public Box(int size) {
        this.size = size;
        this.area = size * size;
        this.rectangles = new ArrayList<>();
    }

    public int getSize() {
        return size;
    }
    public int getArea() { return area; }

    public void addRectangle(Rectangle rectangle, int X, int Y) {
        rectangles.add(rectangle);
        rectangle.setPosition(X, Y);
    }

    public List<Rectangle> getRectangles() {
        return rectangles;
    }

    public int getUsedArea() {
        int usedArea = 0;
        for (Rectangle rectangle : rectangles) {
            usedArea += rectangle.getArea();
        }
        return usedArea;
    }

    public double getUtilization() {
        return ((double) this.getUsedArea() / this.area) * 100;
    }

    public boolean isOverlapping(Rectangle rect1, Rectangle rect2) {
        return (
            rect1.getX() < rect2.getX() + rect2.getWidth() &&
            rect1.getX() + rect1.getWidth() > rect2.getX() &&
            rect1.getY() < rect2.getY() + rect2.getHeight() &&
            rect1.getY() + rect1.getHeight() > rect2.getY()
        );
    }

    public double overlapRate(Rectangle rect1, Rectangle rect2) {
        if (!isOverlapping(rect1, rect2)) {
            return 0.0;
        }
        int xOverlap = Math.min(
                rect1.getX() + rect1.getWidth(),
                rect2.getX() + rect2.getWidth()) - Math.max(rect1.getX(), rect2.getX()
        );
        int yOverlap = Math.min(
                rect1.getY() + rect1.getHeight(),
                rect2.getY() + rect2.getHeight()) - Math.max(rect1.getY(), rect2.getY()
        );
        int overlapArea = xOverlap * yOverlap;
        int largerArea = Math.max(rect1.getArea(), rect2.getArea());
        return (double) overlapArea / largerArea;
    }

    public double totalOverlapRate() {
        double totalRate = 0.0;
        int count = 0;
        for (int i = 0; i < rectangles.size(); i++) {
            for (int j = i + 1; j < rectangles.size(); j++) {
                totalRate += overlapRate(rectangles.get(i), rectangles.get(j));
                count++;
            }
        }
        return count == 0 ? 0.0 : totalRate / count;
    }

    public boolean isOverlapping(Rectangle rectangle) {
        for (Rectangle rect : rectangles) {
            if (isOverlapping(rect, rectangle)) {
                return true;
            }
        }
        return false;
    }

    public boolean isOverflow(Rectangle rectangle) {
        return (
            rectangle.getX() + rectangle.getWidth() > size ||
            rectangle.getY() + rectangle.getHeight() > size
        );
    }

    public Box copy() {
        Box newBox = new Box(this.size);
        for (Rectangle rectangle : this.rectangles) {
            Rectangle newRectangle = rectangle.copy();
            newBox.addRectangle(newRectangle, newRectangle.getX(), newRectangle.getY());
        }
        return newBox;
    }
}
