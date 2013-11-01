package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class AtaxxModel {

    public static final int SINGLE_TOKEN = 1;
    public static final int TWO_TOKENS = 2;

    public static final int NO_VOID_CELLS = 0;

    private static AtaxxModel instance;
    private List<List<TileModel>> board;
    private int numberOfPlay;
    private Owner currentPlayer;
    private int boardSize;
    private int blueTokens;
    private int redTokens;

    private AtaxxModel() {
        board = new ArrayList<>();
    }

    public static AtaxxModel getInstance() {
        if (instance == null) {
           instance = new AtaxxModel();
        }
        return instance;
    }

    public void generate(int size) {
       generate(size, SINGLE_TOKEN);
    }

    public void generate(int size, int startingPieces) {
        generate(size, startingPieces, NO_VOID_CELLS);
    }

    public void generate(int size, int startingPieces, int voidCells) {
        board.clear();
        numberOfPlay = 1;
        boardSize = size;
        currentPlayer = Owner.BLUE;
        for (int i = 0; i < size; i++) {
            board.add(new ArrayList<TileModel>());
            for (int j = 0; j < size; j++) {
                board.get(i).add(new TileModel(i, j));
            }
        }
        setOwnership(size, startingPieces);

        if (voidCells > NO_VOID_CELLS) {
            //TODO Algorithme de génération des cases vides
            board.get(2).get(2).lock();
        }
    }

    private void setOwnership(int size, int startingPieces) {
        switch(startingPieces) {
            case TWO_TOKENS:
                get(0, 0).addPiece(Owner.BLUE);
                get(size - 1, size - 1).addPiece(Owner.BLUE);

                get(size - 1, 0).addPiece(Owner.RED);
                get(0, size - 1).addPiece(Owner.RED);
                redTokens = blueTokens = TWO_TOKENS;
                break;
            default:
                get(0, 0).addPiece(Owner.BLUE);
                get(size - 1, size - 1).addPiece(Owner.RED);
        }
    }
    //TODO ajouter gestion des coups identiques plusieurs fois de suite
    public boolean isGameOver() {
        return blueTokens == 0 || redTokens == 0;
    }

    public Owner getWinner() {
        if (blueTokens == 0) {
            return Owner.RED;
        } else if (redTokens == 0) {
            return Owner.BLUE;
        }
        return null;
    }

    public TileModel get(int x, int y) {
        if (x < 0 && x >= board.size() && y < 0 && y >= board.size()) {
            throw new IllegalArgumentException();
        }
        return board.get(x).get(y);
    }

    public void move(TileModel begin, TileModel end) throws IllegalAccessException {
        boolean canMove = false;
        if (TileModel.isCellAvailable(end)) {
            int endX = end.getPositionX();
            int endY = end.getPositionY();
            if (begin.isNear(end, 1)) {
                board.get(endX).get(endY).addPiece(begin.getOwner());
                end.addPiece(begin.getOwner());
                spread(end);
                canMove = true;
            } else if (begin.isNear(end, 2) && begin.canMove(end)) {
                board.get(endX).get(endY).addPiece(begin.getOwner());
                board.get(begin.getPositionX()).get(begin.getPositionY()).clear();
                spread(board.get(endX).get(endY));
                canMove = true;
            }
        }
        if (canMove) {
            updateTokenNumber();
        } else {
            throw new IllegalAccessException();
        }
    }

    private void updateTokenNumber() {
        blueTokens = 0;
        redTokens = 0;
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                TileModel c = board.get(i).get(j);
                if (Owner.BLUE == c.getOwner()) {
                    blueTokens++;
                }
                if (Owner.RED == c.getOwner()) {
                    redTokens++;
                }
            }
        }
    }

    private void spread(TileModel c) {
        int x = c.getPositionX();
        int y = c.getPositionY();

        for (int i = (x - 1); i <= (x + 1); i++) {
            for (int j = (y - 1); j <= (y + 1); j++) {
                if (i < 0 || j < 0 || i >= boardSize || j >= boardSize) {
                    continue;
                }
                TileModel current = board.get(i).get(j);
                if (current.getOwner() != c.getOwner()
                        && !current.isLocked()
                        && current.getPieceModel() != null) {
                    current.setOwner(c.getOwner());
                    //spread(current);
                }
            }
        }
    }

    public void changePlayer() {
        currentPlayer = currentPlayer.opposite();
        numberOfPlay++;
    }

    public boolean isCurrentPlayerTurn(Owner owner) {
        return this.currentPlayer == owner;
    }

    public int getNumberOfPlay() {
        return numberOfPlay;
    }

    public int getBlueTokens() {
        return blueTokens;
    }

    public int getRedTokens() {
        return redTokens;
    }

    //TODO Remove
    public void print() {
        for (List<TileModel> lc : board) {
            for (TileModel c : lc) {
                System.out.print(c + "   ");
            }
            System.out.println();
        }
    }

}
