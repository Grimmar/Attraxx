package model;

import model.board.Board;
import model.board.DefaultBoard;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class AtaxxModel implements Cloneable {
    public static final int SINGLE_TOKEN = 1;
    public static final int TWO_TOKENS = 2;

    private static AtaxxModel instance;
    private List<List<TileModel>> tiles;
    private Owner currentPlayer;
    private Board board;
    private int boardSize;
    private int blueTokens;
    private int redTokens;
    private int numberOfPlay;

    private AtaxxModel() {
        tiles = new ArrayList<>();
    }

    public static AtaxxModel getInstance() {
        if (instance == null) {
           instance = new AtaxxModel();
        }
        return instance;
    }

    public void generate(int size, int startingPieces) {
        boardSize = size;
        if (board == null) {
            board = new DefaultBoard();
        }
        clear();

        for (int i = 0; i < size; i++) {
            tiles.add(new ArrayList<TileModel>());
            for (int j = 0; j < size; j++) {
                TileModel tile = new TileModel(i, j);
                if(board.isLocked(i, j)) {
                    tile.lock();
                }
                tiles.get(i).add(tile);
            }
        }
        setOwnership(size, startingPieces);
    }

    private void clear() {
        tiles.clear();
        numberOfPlay = 1;
        currentPlayer = Owner.BLUE;
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

    //TODO
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
        if (x < 0 || x >= tiles.size() || y < 0 || y >= tiles.size()) {
            //throw new IndexOutOfBoundsException();
            return null;
        }
        return tiles.get(x).get(y);
    }

    public void move(TileModel begin, TileModel end) throws IllegalAccessException {
        boolean canMove = false;
        if (TileModel.isCellAvailable(end)) {
            int endX = end.getPositionX();
            int endY = end.getPositionY();
            if (begin.isNear(end, 1)) {
                tiles.get(endX).get(endY).addPiece(begin.getOwner());
                end.addPiece(begin.getOwner());
                spread(end);
                canMove = true;
            } else if (begin.isNear(end, 2) && canMoveTo(begin, end)) {
                tiles.get(endX).get(endY).addPiece(begin.getOwner());
                tiles.get(begin.getPositionX()).get(begin.getPositionY()).clear();
                spread(tiles.get(endX).get(endY));
                canMove = true;
            }
        }
        if (canMove) {
            updateTokens();
        } else {
            throw new IllegalAccessException();
        }
    }

    public boolean canMoveTo(TileModel t1, TileModel t2) {
        int i, j;
        if (t1.getPositionX() > t2.getPositionX()) {
            i = t1.getPositionX() - 1;
        }  else if (t1.getPositionX() < t2.getPositionX()) {
            i = t1.getPositionX() + 1;
        } else {
            i = t1.getPositionX();
        }
        if (t1.getPositionY() > t2.getPositionY()) {
            j = t1.getPositionY() - 1;
        } else if (t1.getPositionY() < t2.getPositionY()) {
            j = t1.getPositionY() + 1;
        } else {
            j = t1.getPositionY();
        }
      TileModel middleTile = get(i, j);
      return t1.canMoveTo(t2) && TileModel.isCellAvailable(middleTile);
    }

    public List<TileModel> getPossibleMoves(TileModel source) {
        List<TileModel> tiles = new ArrayList<>();
        int x = source.getPositionX();
        int y = source.getPositionY();
        for (int i = x - 2; i < x + 2; i++) {
            for (int j = y - 2; j < y + 2; j++) {
                TileModel tile = get(i, j);
                if (tile != null && isMoveValid(source, tile)) {
                    tiles.add(tile);
                    System.out.println(tile.getPositionX() + " " + tile.getPositionY());
                }
            }
        }
        return tiles;
    }

    public boolean isMoveValid(TileModel source, TileModel destination) {
        return (destination.isNear(source)) && !destination.isLocked() && canMoveTo(source, destination);
    }

    public boolean isMoveInvalid(TileModel source, TileModel destination) {
        return (destination.getPieceModel() != null || !TileModel.isCellAvailable(destination))
                && canMoveTo(source, destination);
    }

    private void updateTokens() {
        blueTokens = 0;
        redTokens = 0;
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = 0; j < tiles.size(); j++) {
                TileModel c = tiles.get(i).get(j);
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
                TileModel current = tiles.get(i).get(j);
                if (current.getOwner() != c.getOwner()
                        && current.getPieceModel() != null) {
                    current.setOwner(c.getOwner());
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

    public int getBoardSize() {
        return boardSize;
    }

    @Override
    public Object clone() {
        AtaxxModel model = null;
        try {
            model = (AtaxxModel) super.clone();
            List<List<TileModel>> tiles = new ArrayList<>();
            for(int i = 0; i < boardSize; i++){
                List<TileModel> tile = new ArrayList<>();
                for(int j = 0;j < boardSize; j++){
                    tile.add(j, (TileModel)model.get(i,j).clone());
                }
                tiles.add(i, tile);
            }
        } catch(CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }


        model.tiles = new ArrayList<>(tiles);
        model.currentPlayer = currentPlayer;
        return model;
    }

    public void print() {
        for (int i = 0; i < boardSize; i++) {
            System.out.print("|");
            for (int j = 0; j < boardSize; j++) {
                if (get(i, j).getOwner() == Owner.RED) {
                    System.out.print("R|");
                }  else if (get(i, j).getOwner() == Owner.BLUE) {
                    System.out.print("B|");
                }  else {
                    System.out.print(" |");
                }

            }
            System.out.println();
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
    }
}

