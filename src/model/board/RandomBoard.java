package model.board;

import java.util.Random;

public class RandomBoard extends AbstractBoard{

    private static final int MAX_LOCKS = 15;

    private int randomLocks;
    private static Random random;

    static {
        random = new Random();
    }

    public RandomBoard(int size) {
        this(size, random.nextInt(MAX_LOCKS));
    }

    public RandomBoard(int size, int random) {
        super(size);
        this.randomLocks = random;
        placeLocks();
    }

    @Override
    protected boolean autoLock() {
        return false;
    }

    @Override
    protected void placeLocks() {
        for (int i = 0; i < randomLocks; i++) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            if ((x != 0 && y != 0) && (x != 0 && y != size-1)
                    && (x != size - 1 && y != 0) && (x != size - 1 && y != size - 1)){
                lock(y, x);
            } else {
                i--;
            }
        }
    }

    @Override
    public BoardType getType() {
        return BoardType.RANDOM;
    }
}
