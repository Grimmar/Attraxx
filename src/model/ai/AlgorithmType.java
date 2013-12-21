package model.ai;

import model.Nameable;
import model.ai.algorithms.Algorithm;
import model.ai.algorithms.AlphaBeta;
import model.ai.algorithms.MiniMax;
import model.ai.algorithms.Negamax;

public enum AlgorithmType implements Nameable {

    MINIMAX("Minimax") {
                @Override
                public Algorithm make(int depth) {
                    return new MiniMax(depth);
                }
            }, NEGAMAX("Negamax") {
                @Override
                public Algorithm make(int depth) {
                    return new Negamax(depth);
                }
            }, ALPHABETA("AlphaBeta") {
                @Override
                public Algorithm make(int depth) {
                    return new AlphaBeta(depth);
                }
            };
    private final String label;

    private AlgorithmType(String label) {
        this.label = label;
    }

    public String getName() {
        return label;
    }

    public abstract Algorithm make(int depth);
}
