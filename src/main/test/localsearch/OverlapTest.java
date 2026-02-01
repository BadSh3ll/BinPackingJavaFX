package localsearch;

import algorithm.Rectangle;
import algorithm.instance.Instance;
import algorithm.instance.InstanceGenerator;
import algorithm.localsearch.LocalSearchSolver;
import algorithm.localsearch.neighborhood.Neighborhood;
import algorithm.localsearch.neighborhood.Overlap;
import algorithm.localsearch.objective.Objective;
import algorithm.localsearch.objective.OverlapObjective;
import algorithm.solution.OverlapPackingSolution;
import algorithm.solution.PackingSolution;
import app.binpacking.TestEnvironment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.Utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Partially Overlap Allowed Test")
class OverlapTest {


    private static final List<Instance> easyInstances = new ArrayList<>();
    private static final List<Instance> mediumInstances = new ArrayList<>();
    private static final List<Instance> hardInstances = new ArrayList<>();

    private static LocalSearchSolver<OverlapPackingSolution> localSearchSolver;

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

        // Initialize the Local Search Solver
        Neighborhood<OverlapPackingSolution> neighborhood = new Overlap();
        Objective<OverlapPackingSolution> objective = new OverlapObjective();
        localSearchSolver = new LocalSearchSolver<>(neighborhood, objective, 1000);
    }

    @Test
    void easy() {

        List<PackingSolution> solutions = new ArrayList<>();
        List<Long> durations = new ArrayList<>();

        // Solve easy instances and check solutions
        for (Instance instance : easyInstances) {

            OverlapPackingSolution initial = OverlapPackingSolution.init(instance, 1000);
            Date startTime = new Date();
            OverlapPackingSolution solution = localSearchSolver.solve(initial);
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
                "localsearch",
                "Overlap_EasyResults.csv"
        );

        List<String[]> csvData = new ArrayList<>();
        csvData.add(new String[]{ "Instance", "NumBoxes", "Duration(ms)" });
        for (int i = 0; i < solutions.size(); i++) {
            PackingSolution sol = solutions.get(i);
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

        List<PackingSolution> solutions = new ArrayList<>();
        List<Long> durations = new ArrayList<>();

        // Solve medium instances and check solutions
        for (Instance instance : mediumInstances) {

            OverlapPackingSolution initial = OverlapPackingSolution.init(instance, 1000);
            Date startTime = new Date();
            OverlapPackingSolution solution = localSearchSolver.solve(initial);
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
                "localsearch",
                "Overlap_MediumResults.csv"
        );

        List<String[]> csvData = new ArrayList<>();
        csvData.add(new String[]{ "Instance", "NumBoxes", "Duration(ms)" });
        for (int i = 0; i < solutions.size(); i++) {
            PackingSolution sol = solutions.get(i);
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
        List<PackingSolution> solutions = new ArrayList<>();
        List<Long> durations = new ArrayList<>();

        // Solve hard instances and check solutions
        for (Instance instance : hardInstances) {

            OverlapPackingSolution initial = OverlapPackingSolution.init(instance, 1000);
            Date startTime = new Date();
            OverlapPackingSolution solution = localSearchSolver.solve(initial);
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
                "localsearch",
                "Overlap_HardResults.csv"
        );

        List<String[]> csvData = new ArrayList<>();
        csvData.add(new String[]{ "Instance", "NumBoxes", "Duration(ms)" });
        for (int i = 0; i < solutions.size(); i++) {
            PackingSolution sol = solutions.get(i);
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