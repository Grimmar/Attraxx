package model.ai;

public enum DifficultyEnum {
    BEGINNER("Débutant", 1), APPRENTICE("Apprenti", 2), AVERAGE("Normal", 3)
    , EXPERT("Expert", 4), LEGENDARY("Légendaire", 5);
    private final String label;
    private final int depth;

    private DifficultyEnum(String label, int depth) {
        this.label = label;
        this.depth = depth;
    }

    public String getLabel() {
        return label;
    }

    public int getDepth() {
        return depth;
    }
}
