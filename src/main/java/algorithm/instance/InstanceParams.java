package algorithm.instance;

public record InstanceParams(int numRectangles, int boxSize, int minWidth, int minHeight, int maxWidth, int maxHeight) {

    public InstanceParams {
        if (maxWidth < minWidth) {
            throw new IllegalArgumentException("maxWidth must be >= minWidth");
        }
        if (maxHeight < minHeight) {
            throw new IllegalArgumentException(
                    "maxHeight must be >= minHeight"
            );
        }
    }
}
