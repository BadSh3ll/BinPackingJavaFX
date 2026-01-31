package algorithm.localsearch.neighborhood;

public enum NeighborhoodType {

    GEOMETRY("Geometry"),
    PERMUTATION("Permutation"),
    OVERLAP("Overlap");

    private final String displayName;
    NeighborhoodType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static NeighborhoodType fromDisplayName(String displayName) {
        for (NeighborhoodType type : NeighborhoodType.values()) {
            if (type.getDisplayName().equals(displayName)) {
                return type;
            }
        }
        return null;
    }

}
