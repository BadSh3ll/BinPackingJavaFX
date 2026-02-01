package app.binpacking;

import algorithm.instance.InstanceParams;

public class TestEnvironment {
    private final int numInstances;
    private final InstanceParams params;

    private TestEnvironment(int numInstances, InstanceParams params) {
        this.numInstances = numInstances;
        this.params = params;
    }


    public static TestEnvironment EasyCase() {
        return new TestEnvironment(
            100,
            new InstanceParams(
                100,
                500,
                10,
                10,
                400,
                400
            )
        );
    }

    public static TestEnvironment MediumCase() {
        return new TestEnvironment(
            100,
            new InstanceParams(
                1000,
                500,
                10,
                10,
                400,
                400
            )
        );
    }

    public static TestEnvironment HardCase() {
        return new TestEnvironment(
            100,
            new InstanceParams(
                2000,
                500,
                10,
                10,
                400,
                400
            )
        );
    }

    public int numInstances() {
        return numInstances;
    }
    public InstanceParams params() {
        return params;
    }
}
