package algorithm.greedy.putting;

import algorithm.Box;
import algorithm.Rectangle;
import java.util.*;

public class Shelf implements PuttingStrategy {

    private final Map<Box, List<ShelfData>> shelvesMap = new HashMap<>();

    private static class ShelfData {
        private final int y;
        private final int height;
        private int currentX;
        private final List<Rectangle> rectangles;

        public ShelfData(int y, int height, int currentX) {
            this.y = y;
            this.height = height;
            this.currentX = currentX;
            this.rectangles = new ArrayList<>();
        }

        public int getY() {
            return y;
        }

        public int getHeight() {
            return height;
        }

        public int getCurrentX() {
            return currentX;
        }

        public void setCurrentX(int currentX) {
            this.currentX = currentX;
        }

        public List<Rectangle> getRectangles() {
            return rectangles;
        }
    }

    @Override
    public TryPutResult tryPut(Rectangle rectangle, Box box) {
        // Get or initialize shelves
        List<ShelfData> shelves = shelvesMap.get(box);
        if (shelves == null) {
            shelves = initializeShelves(box);
            shelvesMap.put(box, shelves);
        }

        // Try to fit in existing shelves first
        for (ShelfData shelf : shelves) {
            TryPutResult result = tryFitInShelf(rectangle, shelf, box);
            if (result != null) {
                shelf.setCurrentX(shelf.getCurrentX() + (result.rotated() ? rectangle.getHeight() : rectangle.getWidth()));
                return result;
            }
        }

        return tryCreateNewShelf(rectangle, box, shelves);
    }

    private List<ShelfData> initializeShelves(Box box) {
        List<ShelfData> shelves = new ArrayList<>();

        if (box.getRectangles().isEmpty()) {
            return shelves;
        }

        // Group existing rectangles into shelves based on their y-positions
        Map<Integer, List<Rectangle>> yPositions = new HashMap<>();

        for (Rectangle rect : box.getRectangles()) {
            int y = rect.getY();
            yPositions.computeIfAbsent(y, _ -> new ArrayList<>()).add(rect);
        }

        // Create shelf objects
        for (Map.Entry<Integer, List<Rectangle>> entry : yPositions.entrySet()) {
            int y = entry.getKey();
            List<Rectangle> rects = entry.getValue();

            rects.sort(Comparator.comparingInt(Rectangle::getX));
            int maxHeight = rects.stream().mapToInt(Rectangle::getHeight).max().orElse(0);
            int maxX = rects.stream().mapToInt(r -> r.getX() + r.getWidth()).max().orElse(0);

            ShelfData shelf = new ShelfData(y, maxHeight, maxX);
            shelf.getRectangles().addAll(rects);
            shelves.add(shelf);
        }

        shelves.sort(Comparator.comparingInt(ShelfData::getY));
        return shelves;
    }

    private TryPutResult tryCreateNewShelf(Rectangle rectangle, Box box, List<ShelfData> shelves) {
        // Find the lowest available y position for a new shelf
        int newShelfY = 0;
        if (!shelves.isEmpty()) {
            ShelfData lastShelf = shelves.getLast();
            newShelfY = lastShelf.getY() + lastShelf.getHeight();
        }

        // Rule 1: First rectangle on new shelf should be stored sideways
        Rectangle testRect;
        boolean rotated = false;

        if (rectangle.isSideway()) {
            testRect = rectangle;
        } else {
            testRect = rectangle.rotate();
            rotated = true;
        }

        testRect.setPosition(0, newShelfY);

        if (box.isOverflow(testRect)) {
            return null;
        }

        for (Rectangle placedRect : box.getRectangles()) {
            if (box.isOverlapping(testRect, placedRect)) {
                return null;
            }
        }

        // Create the new shelf
        shelves.add(new ShelfData(newShelfY, testRect.getHeight(), testRect.getWidth()));

        return new TryPutResult(0, newShelfY, rotated);
    }

    private TryPutResult tryFitInShelf(Rectangle rectangle, ShelfData shelf, Box box) {
        Rectangle testRect;
        boolean rotated = false;

        // Rule 2: Put the rectangle upright to minimize wasted space
        if (rectangle.isUpright()) {
            testRect = rectangle;
        } else {
            testRect = rectangle.rotate();
            rotated = true;
        }

        if (testRect.getHeight() > shelf.getHeight()) {
            return null;
        }

        testRect.setPosition(shelf.getCurrentX(), shelf.getY());

        if (box.isOverflow(testRect)) {
            return null;
        }

        for (Rectangle placedRect : box.getRectangles()) {
            if (box.isOverlapping(testRect, placedRect)) {
                return null;
            }
        }

        return new TryPutResult(shelf.getCurrentX(), shelf.getY(), rotated);
    }
}
