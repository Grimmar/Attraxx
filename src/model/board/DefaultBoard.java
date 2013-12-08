package model.board;

public class DefaultBoard implements Board {

    @Override
    public boolean isLocked(int i, int j) {
        return false;
    }

    @Override
    public BoardType getType() {
        return BoardType.DEFAULT;
    }
}
