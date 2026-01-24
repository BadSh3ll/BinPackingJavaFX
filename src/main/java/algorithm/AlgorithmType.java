package algorithm;

public enum AlgorithmType {
    GREEDY("Greedy"),
    LOCAL_SEARCH("Local Search");

    private final String name;

    AlgorithmType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
