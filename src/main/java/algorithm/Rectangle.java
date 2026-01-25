package algorithm;

public class Rectangle implements Element {

    private final int width;
    private final int height;
    private final int area;

    // position in Box
    private int x;
    private int y;
    private final boolean rotated; // compared to the original

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
        this.area = width * height;
        this.rotated = false;
    }

    public Rectangle(int width, int height, boolean rotated) {
        this.width = width;
        this.height = height;
        this.area = width * height;
        this.rotated = rotated;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getArea() {
        return area;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isRotated() {
        return rotated;
    }

    public Rectangle rotate() {
        return new Rectangle(height, width, !rotated);
    }

    public boolean isSideway() {
        return width > height;
    }

    public boolean isUpright() {
        return height >= width;
    }
}
