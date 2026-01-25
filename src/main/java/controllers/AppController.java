package controllers;

import algorithm.AlgorithmType;
import algorithm.Box;
import algorithm.Rectangle;
import algorithm.greedy.GreedySolver;
import algorithm.greedy.extender.GreedyExtender;
import algorithm.greedy.extender.GreedyExtenderType;
import algorithm.greedy.ordering.GreedyOrdering;
import algorithm.greedy.ordering.GreedyOrderingType;
import algorithm.greedy.putting.PuttingStrategy;
import algorithm.greedy.putting.PuttingStrategyType;
import algorithm.instance.Instance;
import algorithm.instance.InstanceGenerator;
import algorithm.instance.InstanceParams;
import algorithm.localsearch.LocalSearchSolver;
import algorithm.localsearch.neighborhood.Geometry;
import algorithm.localsearch.neighborhood.Neighborhood;
import algorithm.localsearch.objective.KampkeObjective;
import algorithm.localsearch.objective.Objective;
import algorithm.solution.PackingSolution;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import utils.UIUtils;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AppController {

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
    private ScrollPane ScrollPane;

    @FXML
    private Text Performance;


    @FXML
    private ToggleGroup SelectionStrategy;
    @FXML
    private ToggleGroup PuttingStrategy;
    @FXML
    private ToggleGroup GreedyStrategy;

    @FXML
    private TextField maxIterations;

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


        // Create a background task
        Task<PackingSolution> task = new Task<>() {
            @Override
            protected PackingSolution call() {

                AlgorithmType algorithmType = AlgorithmType.fromString(algorithm.getText());
                return switch (algorithmType) {
                    case GREEDY -> RunGreedy();
                    case LOCAL_SEARCH -> RunLocalSearch();
                };

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
            visualize(boxes);
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

    private PackingSolution RunGreedy() {
        // Get ordering
        RadioButton orderingButton = (RadioButton) SelectionStrategy.getSelectedToggle();
        GreedyOrderingType orderingType = GreedyOrderingType.fromDisplayName(orderingButton.getText());
        assert orderingType != null;
        GreedyOrdering<Rectangle> ordering = UIUtils.getSelectedGreedyOrdering(Objects.requireNonNull(GreedyOrderingType.fromDisplayName(((RadioButton) SelectionStrategy.getSelectedToggle()).getText())));

        // Get putting strategy
        RadioButton puttingButton = (RadioButton) PuttingStrategy.getSelectedToggle();
        PuttingStrategyType puttingType = PuttingStrategyType.fromDisplayName(puttingButton.getText());
        assert puttingType != null;
        PuttingStrategy putting = UIUtils.getSelectedPuttingStrategy(Objects.requireNonNull(PuttingStrategyType.fromDisplayName(((RadioButton) PuttingStrategy.getSelectedToggle()).getText())));

        // Get extender
        RadioButton extenderButton = (RadioButton) GreedyStrategy.getSelectedToggle();
        GreedyExtenderType extenderType = GreedyExtenderType.fromDisplayName(extenderButton.getText());
        assert extenderType != null;
        GreedyExtender<PackingSolution, Rectangle> extender = UIUtils.getSelectedGreedyExtender(Objects.requireNonNull(GreedyExtenderType.fromDisplayName(((RadioButton) GreedyStrategy.getSelectedToggle()).getText())), putting);

        // Solve the instance
        PackingSolution initial = new PackingSolution(instance.boxSize);

        GreedySolver<PackingSolution, Rectangle> solver = new GreedySolver<>(ordering, extender);

        Date startTime = new Date();
        PackingSolution solution = solver.solve(initial, instance.rectangles);
        Date endTime = new Date();
        Performance.setText("Time: " + (endTime.getTime() - startTime.getTime()) + " ms" +
                " | Boxes used: " + solution.boxes().size());

        return solution;
    }

    private PackingSolution RunLocalSearch() {

        // Initial solution via greedy - first fit - largest area - shelf
        PuttingStrategy putting = UIUtils.getSelectedPuttingStrategy(PuttingStrategyType.SHELF);
        GreedyExtender<PackingSolution, Rectangle> extender = UIUtils.getSelectedGreedyExtender(GreedyExtenderType.FIRST_FIT, putting);
        GreedyOrdering<Rectangle> ordering = UIUtils.getSelectedGreedyOrdering(GreedyOrderingType.LARGEST_AREA_FIRST);
        GreedySolver<PackingSolution, Rectangle> greedySolver = new GreedySolver<>(ordering, extender);
        PackingSolution initialSolution = new PackingSolution(instance.boxSize);
        Date startTime = new Date();
        PackingSolution greedySolution = greedySolver.solve(initialSolution, instance.rectangles);

        // Local search
        Neighborhood<PackingSolution> neighborhood = new Geometry();
        Objective<PackingSolution> objective = new KampkeObjective();
        LocalSearchSolver<PackingSolution> localSearchSolver = new LocalSearchSolver<>(neighborhood, objective, Integer.parseInt(maxIterations.getText()));
        PackingSolution improvedSolution = localSearchSolver.solve(greedySolution);
        Date endTime = new Date();

        Performance.setText("Time: " + (endTime.getTime() - startTime.getTime()) + " ms" +
                " | Boxes used: " + improvedSolution.boxes().size() +
                " | Improvement: " + (greedySolution.boxes().size() - improvedSolution.boxes().size()) + " boxes");

        return improvedSolution;
    }

    private void visualize(List<Box> boxes) {
        GridPane Grid = new GridPane();
        Grid.setHgap(10);
        Grid.setVgap(10);
        ScrollPane.setContent(Grid);

        for (int i = 0; i < boxes.size(); i++) {
            Box box = boxes.get(i);
            Pane BoxUI = UIUtils.loadComponent("Box");
            assert BoxUI != null;

            for (int j = 0; j < box.getRectangles().size(); j++) {
                Rectangle rect = box.getRectangles().get(j);
                javafx.scene.shape.Rectangle RectUI = new javafx.scene.shape.Rectangle();

                double scale = BoxUI.getPrefWidth() / instance.boxSize;

                RectUI.setWidth(rect.getWidth() * scale);
                RectUI.setHeight(rect.getHeight() * scale);
                RectUI.setLayoutX(rect.getX() * scale);
                RectUI.setLayoutY(rect.getY() * scale);

                if (rect.isRotated()) {
                    RectUI.setStyle("-fx-fill: #f97316; -fx-stroke: black; -fx-stroke-width: 1;");
                } else {
                    RectUI.setStyle("-fx-fill: #3b82f6; -fx-stroke: black; -fx-stroke-width: 1;");
                }

                BoxUI.getChildren().add(RectUI);
            }

            // A small label showing utilization
            double utilization = box.getUtilization();
            Label utilizationLabel = new Label(String.format("%.2f%%", utilization));
            utilizationLabel.setLayoutX(5);
            utilizationLabel.setLayoutY(5);
            utilizationLabel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-padding: 2;");
            BoxUI.getChildren().add(utilizationLabel);

            Grid.add(BoxUI, i % 4, i / 4);
        }
    }
}
