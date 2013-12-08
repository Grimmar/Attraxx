package model.board;

import model.Nameable;

/**
 * Created with IntelliJ IDEA.
 * User: Quentin
 * Date: 08/12/13
 * Time: 19:29
 * To change this template use File | Settings | File Templates.
 */
public enum BoardType implements Nameable {
    DEFAULT("Normal") {
        @Override
        public Board make(int size) {
            return new DefaultBoard();
        }
    }, CROSS("Croix") {
        @Override
        public Board make(int size) {
            return new CrossBoard(size);
        }
    }, RANDOM("Au hasard") {
        @Override
        public Board make(int size) {
            return new RandomBoard(size);
        }
    }, SQUARE("Carré") {
        @Override
        public Board make(int size) {
            return new SquareBoard(size);
        }
    };
    private final String label;

    private BoardType(String label) {
        this.label = label;
    }

    public String getName() {
        return label;
    }

    public abstract Board make(int size);
}
