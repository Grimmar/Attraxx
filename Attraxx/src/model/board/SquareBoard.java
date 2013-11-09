package model.board;

public class SquareBoard extends AbstractBoard {

    public SquareBoard(int size) {
        super(size);
    }

    @Override
    protected void placeLocks() {
        lock(1, 2);
        lock(2, 1);
        lock(size - 3, 1);
        lock(size - 2, 2);
        lock(1, size - 3);
        lock(2, size - 2);
        lock(size - 3, size - 2);
        lock(size - 2, size - 3);
    }
}
