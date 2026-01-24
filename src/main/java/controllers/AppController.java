package controllers;

import algorithm.AlgorithmType;
import algorithm.Box;
import algorithm.Rectangle;
import algorithm.greedy.GreedySolver;
import algorithm.greedy.extender.FirstFitPlacer;
import algorithm.greedy.extender.GreedyExtender;
import algorithm.greedy.ordering.LargestAreaFirst;
import algorithm.greedy.putting.BottemLeft;
import algorithm.instance.Instance;
import algorithm.instance.InstanceGenerator;
import algorithm.instance.InstanceParams;
import algorithm.solution.PackingSolution;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import utils.UIUtils;
import java.util.Date;
import java.util.List;

public class AppController {

    @FXML
    private VBox App;

    @FXML
    private TextField numRectangles;

    @FXML
    private TextField boxSize;

    @FXML
    private TextField minWidth;

    @FXML
    private TextField minHeight;

    @FXML
    private TextField maxWidth;

    @FXML
    private TextField maxHeight;

    @FXML
    private MenuButton algorithm;

    @FXML
    private Button Generate;

    @FXML
    private Button Run;

    @FXML
    private AnchorPane GreedyConfig;

    @FXML
    private AnchorPane LocalSearchConfig;

    @FXML
    private ProgressIndicator running1;

    @FXML
    private ProgressIndicator running2;

    @FXML
    private GridPane Grid;

    @FXML
    private void initialize() {
        // force the fields to be numeric only
        UIUtils.enforceNumeric(numRectangles);
        UIUtils.enforceNumeric(boxSize);
        UIUtils.enforceNumeric(minWidth);
        UIUtils.enforceNumeric(minHeight);
        UIUtils.enforceNumeric(maxWidth);
        UIUtils.enforceNumeric(maxHeight);

        running1.setVisible(false);
        running2.setVisible(false);
        Run.setDisable(true);

        Grid.setMaxHeight(Double.MAX_VALUE);
    }

    @FXML
    public void onGreedyChosen() {
        algorithm.setText(AlgorithmType.GREEDY.getName());
        GreedyConfig.setDisable(false);
        GreedyConfig.setOpacity(1);
        LocalSearchConfig.setDisable(true);
        LocalSearchConfig.setOpacity(0);
    }

    @FXML
    public void onLocalSearchChosen() {
        algorithm.setText(AlgorithmType.LOCAL_SEARCH.getName());
        GreedyConfig.setDisable(true);
        GreedyConfig.setOpacity(0);
        LocalSearchConfig.setDisable(false);
        LocalSearchConfig.setOpacity(1);
    }

    @FXML
    public void generateInstance() {
        Generate.setDisable(true);
        running1.setVisible(true);
        InstanceParams params = new InstanceParams(
            Integer.parseInt(numRectangles.getText()),
            Integer.parseInt(boxSize.getText()),
            Integer.parseInt(minWidth.getText()),
            Integer.parseInt(minHeight.getText()),
            Integer.parseInt(maxWidth.getText()),
            Integer.parseInt(maxHeight.getText())
        );
        System.out.println("Generating instance...");
        instance = InstanceGenerator.generate(params);
        Generate.setDisable(false);
        running1.setVisible(false);
        Run.setDisable(false);
    }

    private Instance instance;

    @FXML
    public void RunAlgorithm() {
        // UI updates
        running1.setVisible(true);
        running2.setVisible(true);
        Generate.setText("");
        Generate.setDisable(true);
        Run.setText("");
        Run.setDisable(true);

        Grid.getChildren().clear();

        // Create a background task
        Task<PackingSolution> task = new Task<>() {
            @Override
            protected PackingSolution call() {
                // Solving
                LargestAreaFirst ordering = new LargestAreaFirst();
                BottemLeft putting = new BottemLeft();
                GreedyExtender<PackingSolution, Rectangle> extender =
                        new FirstFitPlacer(putting);

                PackingSolution initial = new PackingSolution(instance.boxSize);

                GreedySolver<PackingSolution, Rectangle> solver = new GreedySolver<>(ordering, extender);

                Date startTime = new Date();
                PackingSolution solution = solver.solve(initial, instance.rectangles);
                Date endTime = new Date();
                System.out.println("Solved in " + (endTime.getTime() - startTime.getTime()));

                return solution;
            }
        };

        // When task succeeds, update UI
        task.setOnSucceeded(_ -> {
            PackingSolution solution = task.getValue();

            // Update UI components
            running1.setVisible(false);
            running2.setVisible(false);
            Generate.setDisable(false);
            Generate.setText("Generate Instance");
            Run.setDisable(false);
            Run.setText("Run");

            List<Box> boxes = solution.boxes();

            for (int i = 0; i < 16; i++) {
                Node empty = UIUtils.loadComponent("EmptyBox");
                Grid.add(empty, i % 4, i / 4);
            }
            for (int i = 16; i < boxes.size() + 16; i++) {
//              Box box = boxes.get(i - 16);
                Pane BoxUI = UIUtils.loadComponent("Box");
                Grid.add(BoxUI, i % 4, i / 4);
            }
        });

        // Handle failures
        task.setOnFailed(_ -> {
            running1.setVisible(false);
            running2.setVisible(false);
            Generate.setDisable(false);
            Generate.setText("Generate Instance");
            Run.setDisable(false);
            Run.setText("Run");

            System.err.println("Algorithm failed: " + task.getException());
        });

        // Start the task on a background thread
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
