package model.board;

public class CrossBoard extends AbstractBoard {

    public CrossBoard(int size) {
        super(size);
    }

    @Override
    protected void placeLocks() {
        switch(size) {
            case 8:
                lock(2, 3);
                lock(2, 4);
                lock(3, 2);
                lock(4, 2);
                lock(5, 3);
                lock(5, 4);
                lock(3, 5);
                lock(4, 5);
                break;
            case 9:
                lock(3, 4);
                lock(4, 3);
                lock(4, 5);
                lock(5, 4);
                break;
            case 10:
                lock(3, 4);
                lock(3, 5);
                lock(4, 3);
                lock(5, 3);
                lock(6, 4);
                lock(6, 5);
                lock(4, 6);
                lock(5, 6);
                break;
            default:
                lock(2, 3);
                lock(3, 2);
                lock(3, 4);
                lock(4, 3);
        }
    }

    @Override
    public BoardType getType() {
        return BoardType.CROSS;
    }
}
