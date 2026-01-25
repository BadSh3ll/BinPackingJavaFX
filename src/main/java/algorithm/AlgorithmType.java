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
    public static AlgorithmType fromString(String name) {
        for (AlgorithmType type : AlgorithmType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No AlgorithmType with name " + name);
    }
}
