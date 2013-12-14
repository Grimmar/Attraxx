package model;

import model.ai.AlgorithmEnum;
import model.ai.DifficultyEnum;
import model.ai.algorithms.Algorithm;
import model.ai.tree.Node;
import model.board.*;

import java.util.ArrayList;
import java.util.List;

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
            List<TileModel> l = new ArrayList<>();
            for (int j = 0; j < boardSize; j++) {
                TileModel tile = new TileModel(j, i);
                if (board.isLocked(j, i)) {
                    tile.lock();
                }
                l.add(j, tile);
            }
            tiles.add(i, l);
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
        return tiles.get(y).get(x);
    }

    public void move(TileModel begin, TileModel end) throws IllegalAccessException {
        boolean canMove = false;
        if (TileModel.isCellAvailable(end)) {
            if (begin.isNear(end, 1)) {
                end.addPiece(begin.getOwner());
                spread(end);
                canMove = true;
            } else if (begin.isNear(end, 2) && canMoveTo(begin, end)) {
                end.addPiece(begin.getOwner());
                begin.clear();
                spread(end);
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

    public boolean canMoveTo(TileModel begin, TileModel end) {
        int x, y;
        if (begin.getPositionX() > end.getPositionX()) {
            x = begin.getPositionX() - 1;
        } else if (begin.getPositionX() < end.getPositionX()) {
            x = begin.getPositionX() + 1;
        } else {
            x = begin.getPositionX();
        }
        if (begin.getPositionY() > end.getPositionY()) {
            y = begin.getPositionY() - 1;
        } else if (begin.getPositionY() < end.getPositionY()) {
            y = begin.getPositionY() + 1;
        } else {
            y = begin.getPositionY();
        }
        TileModel middleTile = get(x, y);
        return begin.canMoveTo(end) && TileModel.isCellAvailable(middleTile);
    }

    public List<TileModel> getPossibleMoves(TileModel source) {
        List<TileModel> ts = new ArrayList<>();
        int x = source.getPositionX();
        int y = source.getPositionY();
        for (int j = (x - 2); j <= (x + 2); j++) {
            for (int i = (y - 2); i <= (y + 2); i++) {
                if (!(i < 0 || j <0 || i >= boardSize || j >= boardSize)) {
                    TileModel tile = get(j, i);
                    if (TileModel.isCellAvailable(tile)
                            && isMoveValid(source, tile)) {
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
                TileModel c = get(j, i);
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
        for (int j = (x - 1); j <= (x + 1); j++) {
            for (int i = (y - 1); i <= (y + 1); i++) {
                if (!(j < 0 || i < 0 || j >= boardSize || i >= boardSize)) {
                    TileModel current = get(j, i);
                    if (current.getOwner() != c.getOwner()
                            && current.getPieceModel() != null) {
                        current.setOwner(current.getOwner().opposite());
                    }
                }
            }
        }
    }

    private void changePlayer() {
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
        if (Owner.RED == currentPlayer) {
            algorithm.buildTree(this);
            Node result = algorithm.run();
            TileModel start = result.getTile();
            TileModel end = result.getTileEnd();
            try {
                move(get(start.getPositionX(), start.getPositionY()), get(end.getPositionX(),end.getPositionY()));
            } catch (IllegalAccessException ex) {
                System.out.println("Problème lors du déplacement de la pièce en "
                    + end.getPositionX() + " : " + end.getPositionY());
            }
        }
        numberOfPlay++;
    }

    public boolean isCurrentPlayerTurn(Owner owner) {
        return this.currentPlayer == owner;
    }

    public int getBoardSize() {
        return boardSize;
    }

    @Override
    public Object clone() {
        AtaxxModel o = null;
        try {
            o = (AtaxxModel) super.clone();
            o.tiles = new ArrayList<>();
            for (int i = 0; i < boardSize; i++) {
                List<TileModel> l = new ArrayList<>();
                for (int j = 0; j < boardSize; j++) {
                    l.add(j, (TileModel) get(j, i).clone());
                }
                o.tiles.add(i, l);
            }
        } catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        return o;
    }

    public void print() {
        for (int i = 0; i < boardSize; i++) {
            System.out.print("|");
            for (int j = 0; j < boardSize; j++) {
                if (get(j, i).getOwner() == Owner.RED) {
                    System.out.print("R|");
                } else if (get(j, i).getOwner() == Owner.BLUE) {
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

    public boolean isGameOver() {
        return blueTokens == 0 || redTokens == 0
                || blueTokens + redTokens == boardSize * boardSize;
    }

    public Owner getWinner() {
        if (isGameOver()) {
            return blueTokens > redTokens ? Owner.BLUE
                    : (redTokens > blueTokens ? Owner.RED
                    : null);
        }
        return null;

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
