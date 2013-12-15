package model.ai;

import model.Nameable;

public enum DifficultyType implements Nameable {
    BEGINNER("Débutant", 1), APPRENTICE("Apprenti", 2)
    , EXPERT("Expert", 3), LEGENDARY("Légendaire", 4);
    private final String label;
    private final int depth;

    private DifficultyType(String label, int depth) {
        this.label = label;
        this.depth = depth;
    }

    public String getName() {
        return label;
    }

    public int getDepth() {
        return depth;
    }
}
