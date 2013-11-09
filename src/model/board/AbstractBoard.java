package model.board;

import model.PieceModel;
import model.TileModel;

public abstract class AbstractBoard implements Board {

    protected int size;
    private boolean[][] locks;

    public AbstractBoard(int size) {
        this.size = size;
        this.locks = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                locks[i][j] = false;
            }
        }
        if (autoLock()) {
            placeLocks();
        }
    }

    protected void lock(int i, int j) {
        locks[i][j] = true;
    }

    @Override
    public boolean isLocked(int i, int j) {
        return locks[i][j];
    }

    protected abstract void placeLocks();

    protected boolean autoLock() {
        return true;
    }
}
