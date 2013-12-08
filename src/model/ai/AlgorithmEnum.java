package model.ai;

public enum AlgorithmEnum {
    MINIMAX("Minimax");
    private final String label;

    private AlgorithmEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
