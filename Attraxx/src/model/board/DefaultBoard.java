package model.board;

public class DefaultBoard implements Board {

    @Override
    public boolean isLocked(int i, int j) {
        return false;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
