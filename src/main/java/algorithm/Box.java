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
