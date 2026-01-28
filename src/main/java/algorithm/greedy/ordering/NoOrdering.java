package algorithm.greedy.ordering;

import algorithm.Rectangle;

import java.util.List;

public class NoOrdering implements GreedyOrdering<Rectangle> {
    @Override
    public List<Rectangle> order(List<Rectangle> elements) {
        return elements;
    }
}
