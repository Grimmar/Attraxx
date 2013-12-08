package model.ai;

import model.Nameable;
import model.ai.algorithms.Algorithm;
import model.ai.algorithms.MiniMax;

public enum AlgorithmEnum implements Nameable {
    MINIMAX("Minimax") {
        @Override
        public Algorithm make(int depth) {
            return new MiniMax(depth);
        }
    };
    private final String label;

    private AlgorithmEnum(String label) {
        this.label = label;
    }

    public String getName() {
        return label;
    }

    public abstract Algorithm make(int depth);
}
