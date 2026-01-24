package algorithm.greedy.putting;

import algorithm.Box;
import algorithm.Rectangle;

public interface PuttingStrategy {
    TryPutResult tryPut(Rectangle rectangle, Box box);
}
