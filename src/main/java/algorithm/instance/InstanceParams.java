package algorithm.instance;

public class InstanceParams {

    private final int numRectangles;
    private final int boxSize;
    private final int minWidth;
    private final int minHeight;
    private final int maxWidth;
    private final int maxHeight;

    public InstanceParams(
        int numRectangles,
        int boxSize,
        int minWidth,
        int minHeight,
        int maxWidth,
        int maxHeight
    ) {
        if (maxWidth < minWidth) {
            throw new IllegalArgumentException("maxWidth must be >= minWidth");
        }
        if (maxHeight < minHeight) {
            throw new IllegalArgumentException(
                "maxHeight must be >= minHeight"
            );
        }
        this.numRectangles = numRectangles;
        this.boxSize = boxSize;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    public int getNumRectangles() {
        return numRectangles;
    }

    public int getBoxSize() {
        return boxSize;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }
}
