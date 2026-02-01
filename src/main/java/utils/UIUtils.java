package utils;

import algorithm.Rectangle;
import algorithm.greedy.extender.BestFitPlacer;
import algorithm.greedy.extender.FirstFitPlacer;
import algorithm.greedy.extender.GreedyExtender;
import algorithm.greedy.extender.GreedyExtenderType;
import algorithm.greedy.ordering.GreedyOrdering;
import algorithm.greedy.ordering.GreedyOrderingType;
import algorithm.greedy.ordering.LargestAreaFirst;
import algorithm.greedy.ordering.LargestSideFirst;
import algorithm.greedy.putting.*;
import algorithm.solution.PackingSolution;
import app.binpacking.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

import java.util.Objects;

public class UIUtils {

    public static void enforceNumeric(TextField field) {
        field
            .textProperty()
            .addListener(
                    (_, _, newValue) -> {
                        if (!newValue.matches("\\d*")) {
                            field.setText(newValue.replaceAll("\\D", ""));
                        }
                    }
            );
    }

    public static <T> T loadComponent(String component) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(component + ".fxml"));
            fxmlLoader.load();
            return fxmlLoader.getRoot();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static GreedyOrdering<Rectangle> getSelectedGreedyOrdering(GreedyOrderingType type) {
        switch (type) {
            case LARGEST_AREA_FIRST -> {
                return new LargestAreaFirst();
            }
            case LARGEST_SIDE_FIRST -> {
                 return new LargestSideFirst();
            }
            default -> {
                return null;
            }
        }
    }

    public static PuttingStrategy getSelectedPuttingStrategy(PuttingStrategyType type) {
        if (Objects.requireNonNull(type) == PuttingStrategyType.SHELF) {
            return new Shelf();
        }
        return new BottomLeft();
    }

    public static GreedyExtender<PackingSolution, Rectangle> getSelectedGreedyExtender(GreedyExtenderType type, PuttingStrategy putting) {
        switch (type) {
            case FIRST_FIT -> {
                return new FirstFitPlacer(putting);
            }
            case BEST_FIT -> {
                 return new BestFitPlacer(putting);
            }
            default -> {
                return null;
            }
        }
    }
}
