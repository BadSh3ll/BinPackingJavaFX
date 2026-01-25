package algorithm.greedy.extender;

public enum GreedyExtenderType {
    FIRST_FIT("First Fit"),
    BEST_FIT("Best Fit");

    private final String displayName;

    GreedyExtenderType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static GreedyExtenderType fromDisplayName(String displayName) {
        for (GreedyExtenderType type : GreedyExtenderType.values()) {
            if (type.getDisplayName().equals(displayName)) {
                return type;
            }
        }
        return null;
    }
}
