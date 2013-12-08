package model;

import model.ai.AlgorithmEnum;
import model.ai.DifficultyEnum;
import model.ai.algorithms.Algorithm;
import model.ai.tree.Node;
import model.board.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class AtaxxModel implements Cloneable {

    private static AtaxxModel instance;
    private List<List<TileModel>> tiles;
    private Owner currentPlayer;
    private Board board;
    private int boardSize;
    private int blueTokens;
    private int redTokens;
    private int numberOfPlay;
    private boolean gameVSComputer;
    private Algorithm algorithm;

    private AtaxxModel() {
        tiles = new ArrayList<>();
    }

    public static AtaxxModel getInstance() {
        if (instance == null) {
            instance = new AtaxxModel();
        }
        return instance;
    }
    public void generate(AtaxxConfiguration c) {
        boardSize = c.getBoardSize();
        this.gameVSComputer = c.isGameVSComputer();
        if (c.getBoardType() == null) {
            board = new DefaultBoard();
        } else {
            board = c.getBoardType().make(boardSize);
        }
        tiles.clear();
        numberOfPlay = 1;
        currentPlayer = Owner.BLUE;

        for (int i = 0; i < boardSize; i++) {
            tiles.add(new ArrayList<TileModel>());
            for (int j = 0; j < boardSize; j++) {
                TileModel tile = new TileModel(i, j);
                if (board.isLocked(i, j)) {
                    tile.lock();
                }
                tiles.get(i).add(tile);
            }
        }
        setOwnership(boardSize, c.isSingleToken());
        setAlgorithm(c.getAlgorithm(), c.getDifficulty());
    }

    private void setOwnership(int size, boolean singleToken) {
        if (singleToken) {
            get(0, 0).addPiece(Owner.BLUE);
            get(size - 1, size - 1).addPiece(Owner.RED);
        } else {
            get(0, 0).addPiece(Owner.BLUE);
            get(size - 1, size - 1).addPiece(Owner.BLUE);
            get(size - 1, 0).addPiece(Owner.RED);
            get(0, size - 1).addPiece(Owner.RED);
        }
        updateTokens();
    }

    public TileModel get(int x, int y) {
        if (x < 0 || x >= tiles.size() || y < 0 || y >= tiles.size()) {
            return null;
        }
        return tiles.get(x).get(y);
    }

    public void move(TileModel begin, TileModel end) throws IllegalAccessException {
        boolean canMove = false;
        int endX = end.getPositionX();
        int endY = end.getPositionY();
        if (TileModel.isCellAvailable(end)) {
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
            throw new IllegalAccessException("Error in move");
        }
        changePlayer();
        if (!canPlay()){
            changePlayer();
        }
    }

    public boolean canMoveTo(TileModel t1, TileModel t2) {
        int i, j;
        if (t1.getPositionX() > t2.getPositionX()) {
            i = t1.getPositionX() - 1;
        } else if (t1.getPositionX() < t2.getPositionX()) {
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
        List<TileModel> ts = new ArrayList<>();
        int x = source.getPositionX();
        int y = source.getPositionY();
        for (int i = (x - 2); i <= (x + 2); i++) {
            for (int j = (y - 2); j <= (y + 2); j++) {
                if (!(i < 0 ||j <0 || i >= boardSize ||j >= boardSize )) {
                    TileModel tile = get(i, j);
                    if (isMoveValid(source, tile) && TileModel.isCellAvailable(tile)) {
                        ts.add(tile);
                    }
                }
            }
        }
        return ts;
    }

    public boolean isMoveValid(TileModel source, TileModel destination) {
        return (destination.isNear(source))
                && !destination.isLocked()
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
        numberOfPlay++;
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

    private void changePlayer() {
        this.print();
        currentPlayer = currentPlayer.opposite();
    }

    public boolean canPlay() {
        for (List<TileModel> l : tiles) {
            for (TileModel t : l) {
                 if (currentPlayer == t.getOwner()
                     && !getPossibleMoves(t).isEmpty()) {
                     return true;
                 }
            }
        }
        return false;
    }

    public void play() {
        if (Owner.RED == currentPlayer){
            algorithm.buildTree(this);
            Node n = algorithm.run(algorithm.getRoot());
            TileModel start = n.getTile();
            TileModel end = n.getTileEnd();
            try {
                move(get(start.getPositionX(),start.getPositionY()), get(end.getPositionX(),end.getPositionY()));
            } catch (IllegalAccessException ex) {
                System.out.println("Oups ! fail");
            }
        }
        numberOfPlay++;
    }

    public boolean isCurrentPlayerTurn(Owner owner) {
        return this.currentPlayer == owner;
    }
    
    public int getTokens(Owner o){
        updateTokens();
        if(o.equals(Owner.BLUE)){
            return blueTokens;
        }
        if(o.equals(Owner.RED)){
            return redTokens;
        }
        return boardSize * boardSize - blueTokens - redTokens;
    }

    public int getBoardSize() {
        return boardSize;
    }

    @Override
    public Object clone() {
        AtaxxModel model = null;
        List<List<TileModel>> ts = new ArrayList<>();
        try {
            model = (AtaxxModel) super.clone();
            for (int i = 0; i < boardSize; i++) {
                List<TileModel> tile = new ArrayList<>();
                for (int j = 0; j < boardSize; j++) {
                    tile.add(j, (TileModel) this.get(i, j).clone());
                }
                ts.add(i, tile);
            }
        } catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }

        model.tiles = ts;
        model.currentPlayer = currentPlayer;
        return model;
    }

    public void print() {
        for (int i = 0; i < boardSize; i++) {
            System.out.print("|");
            for (int j = 0; j < boardSize; j++) {
                if (get(i, j).getOwner() == Owner.RED) {
                    System.out.print("R|");
                } else if (get(i, j).getOwner() == Owner.BLUE) {
                    System.out.print("B|");
                } else {
                    System.out.print(" |");
                }
            }
            System.out.println();
        }
    }

    public void setAlgorithm(AlgorithmEnum a, DifficultyEnum d) {
        int depth = d.getDepth();
        this.algorithm = a.make(depth);
    }

    public boolean isGameVSComputer() {
        return gameVSComputer;
    }

    //TODO
    public boolean isGameOver() {
        return blueTokens == 0 || redTokens == 0
                || blueTokens + redTokens == boardSize * boardSize;
    }

    //TODO
    public Owner getWinner() {
        return blueTokens > redTokens ? Owner.BLUE
                : (redTokens > blueTokens ? Owner.RED
                : null) ;
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
}
