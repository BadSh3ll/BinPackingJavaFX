package greedy;

import algorithm.Rectangle;
import algorithm.greedy.GreedySolver;
import algorithm.greedy.extender.FirstFitPlacer;
import algorithm.greedy.extender.GreedyExtender;
import algorithm.greedy.ordering.GreedyOrdering;
import algorithm.greedy.ordering.LargestSideFirst;
import algorithm.greedy.putting.BottomLeft;
import algorithm.greedy.putting.PuttingStrategy;
import algorithm.instance.Instance;
import algorithm.instance.InstanceGenerator;
import algorithm.solution.PackingSolution;
import algorithm.solution.Solution;
import app.binpacking.TestEnvironment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.Utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("First Fit Decreasing Side with BottomLeft Putting Strategy Test")
class FirstFitSideBottomLeftTest {


    private static final List<Instance> easyInstances = new ArrayList<>();
    private static final List<Instance> mediumInstances = new ArrayList<>();
    private static final List<Instance> hardInstances = new ArrayList<>();

    private static GreedySolver<PackingSolution, Rectangle> solver;

    @BeforeAll
    static void setUp() {
        TestEnvironment easy = TestEnvironment.EasyCase();
        TestEnvironment medium = TestEnvironment.MediumCase();
        TestEnvironment hard = TestEnvironment.HardCase();

        for (int i = 0; i < easy.numInstances(); i++) {
            Instance instance = InstanceGenerator.generate(easy.params());
            easyInstances.add(instance);
        }

        for (int i = 0; i < medium.numInstances(); i++) {
            Instance instance = InstanceGenerator.generate(medium.params());
            mediumInstances.add(instance);
        }

        for (int i = 0; i < hard.numInstances(); i++) {
            Instance instance = InstanceGenerator.generate(hard.params());
            hardInstances.add(instance);
        }


        PuttingStrategy putting = new BottomLeft();
        GreedyOrdering<Rectangle> ordering = new LargestSideFirst();
        GreedyExtender<PackingSolution, Rectangle> extender =
                new FirstFitPlacer(putting);

        solver = new GreedySolver<>(ordering, extender);
    }

    @Test
    void easy() {

        List<Solution> solutions = new ArrayList<>();
        List<Long> durations = new ArrayList<>();

        // Solve easy instances and check solutions
        for (Instance instance : easyInstances) {

            PackingSolution initial = new PackingSolution(instance.boxSize);

            Date startTime = new Date();
            PackingSolution solution = solver.solve(initial, instance.rectangles);
            Date endTime = new Date();
            long duration = endTime.getTime() - startTime.getTime();

            // Store solution and duration
            solutions.add(solution);
            durations.add(duration);

            // Basic assertions
            assertNotNull(solution);
            assertFalse(solution.boxes().isEmpty());
            // Check that there are no overlapping rectangles in each box
            for (var box : solution.boxes()) {
                assertEquals(0.0, box.totalOverlapRate());

                // Check that no rectangle overflows the box
                for (Rectangle rectangle: box.getRectangles()) {
                    assertFalse(box.isOverflow(rectangle));
                }
            }
        }

        Path path = Paths.get(
                "target",
                "csv",
                "greedy",
                "FirstFitSideBottomLeft_EasyResults.csv"
        );

        List<String[]> csvData = new ArrayList<>();
        csvData.add(new String[]{ "Instance", "NumBoxes", "Duration(ms)" });
        for (int i = 0; i < solutions.size(); i++) {
            PackingSolution sol = (PackingSolution) solutions.get(i);
            String[] data = {
                    String.valueOf(i + 1),
                    String.valueOf(sol.boxes().size()),
                    String.valueOf(durations.get(i))
            };
            csvData.add(data);
        }
        Utils.WriteData(csvData, path);
    }

    @Test
    void medium() {

        List<Solution> solutions = new ArrayList<>();
        List<Long> durations = new ArrayList<>();

        // Solve medium instances and check solutions
        for (Instance instance : mediumInstances) {

            PackingSolution initial = new PackingSolution(instance.boxSize);

            Date startTime = new Date();
            PackingSolution solution = solver.solve(initial, instance.rectangles);
            Date endTime = new Date();
            long duration = endTime.getTime() - startTime.getTime();

            // Store solution and duration
            solutions.add(solution);
            durations.add(duration);

            // Basic assertions
            assertNotNull(solution);
            assertFalse(solution.boxes().isEmpty());
            // Check that there are no overlapping rectangles in each box
            for (var box : solution.boxes()) {
                assertEquals(0.0, box.totalOverlapRate());

                // Check that no rectangle overflows the box
                for (Rectangle rectangle: box.getRectangles()) {
                    assertFalse(box.isOverflow(rectangle));
                }
            }
        }

        Path path = Paths.get(
                "target",
                "csv",
                "greedy",
                "FirstFitSideBottomLeft_MediumResults.csv"
        );

        List<String[]> csvData = new ArrayList<>();
        csvData.add(new String[]{ "Instance", "NumBoxes", "Duration(ms)" });
        for (int i = 0; i < solutions.size(); i++) {
            PackingSolution sol = (PackingSolution) solutions.get(i);
            String[] data = {
                    String.valueOf(i + 1),
                    String.valueOf(sol.boxes().size()),
                    String.valueOf(durations.get(i))
            };
            csvData.add(data);
        }
        Utils.WriteData(csvData, path);
    }

    @Test
    void hard() {
        List<Solution> solutions = new ArrayList<>();
        List<Long> durations = new ArrayList<>();

        // Solve hard instances and check solutions
        for (Instance instance : hardInstances) {

            PackingSolution initial = new PackingSolution(instance.boxSize);

            Date startTime = new Date();
            PackingSolution solution = solver.solve(initial, instance.rectangles);
            Date endTime = new Date();
            long duration = endTime.getTime() - startTime.getTime();

            // Store solution and duration
            solutions.add(solution);
            durations.add(duration);

            // Basic assertions
            assertNotNull(solution);
            assertFalse(solution.boxes().isEmpty());
            // Check that there are no overlapping rectangles in each box
            for (var box : solution.boxes()) {
                assertEquals(0.0, box.totalOverlapRate());

                // Check that no rectangle overflows the box
                for (Rectangle rectangle: box.getRectangles()) {
                    assertFalse(box.isOverflow(rectangle));
                }
            }
        }

        Path path = Paths.get(
                "target",
                "csv",
                "greedy",
                "FirstFitSideBottomLeft_HardResults.csv"
        );

        List<String[]> csvData = new ArrayList<>();
        csvData.add(new String[]{ "Instance", "NumBoxes", "Duration(ms)" });
        for (int i = 0; i < solutions.size(); i++) {
            PackingSolution sol = (PackingSolution) solutions.get(i);
            String[] data = {
                    String.valueOf(i + 1),
                    String.valueOf(sol.boxes().size()),
                    String.valueOf(durations.get(i))
            };
            csvData.add(data);
        }
        Utils.WriteData(csvData, path);
    }
}