/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class AtaxxModel {

    private List<List<Cell>> board;
    private int round;
    private Player player;

    public AtaxxModel() {
        board = new ArrayList<>();
    }

    public void generate(int size) {
        board.clear();
        round = 1;
        player = Player.FIRST_PLAYER;
        for (int i = 0; i < size; i++) {
            board.add(new ArrayList<Cell>());
            for (int j = 0; j < size; j++) {
                Cell c = new Cell(i, j);
                if (i == 0 && j == 0) {
                    c.setPlayer(Player.FIRST_PLAYER);
                }
                if (i == size - 1 && j == size - 1) {
                    c.setPlayer(Player.SECOND_PLAYER);
                }
                board.get(i).add(c);
            }
        }
    }

    public Player isWin() {
        int nbPlayer1 = 0;
        int nbPlayer2 = 0;
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                if (board.get(i).get(j).getPlayer() == Player.FIRST_PLAYER) {
                    nbPlayer1++;
                }
                if (board.get(i).get(j).getPlayer() == Player.SECOND_PLAYER) {
                    nbPlayer2++;
                }
            }
        }
        if (nbPlayer1 == 0) {
            return Player.FIRST_PLAYER;
        }
        if (nbPlayer2 == 0) {
            return Player.SECOND_PLAYER;
        }
        return null;
    }

    public void print() {
        for (List<Cell> lc : board) {
            for (Cell c : lc) {
                System.out.print(c + "   ");
            }
            System.out.println();
        }
    }

    public Cell get(int x, int y) {
        if (x < 0 && x >= board.size() && y < 0 && y >= board.size()) {
            throw new IllegalArgumentException();
        }
        return board.get(x).get(y);
    }

    public void move(Cell begin, Cell end) throws IllegalAccessException {
        if (begin.isNear(end, 1)) {
            board.get(end.getPositionX()).get(end.getPositionY()).setPlayer(begin.getPlayer());
            end.setPlayer(begin.getPlayer());
            spread(end);
        } else {
            if (begin.isNear(end, 2) && begin.isMovePossible(end)) {
                board.get(end.getPositionX()).get(end.getPositionY()).setPlayer(begin.getPlayer());
                board.get(begin.getPositionX()).get(begin.getPositionY()).setPlayer(null);
                spread(board.get(end.getPositionX()).get(end.getPositionY()));
            } else{
                throw new IllegalAccessException();
            }
        }
    }

    private void spread(Cell c) {
        int x = c.getPositionX();
        int y = c.getPositionY();

        for (int i = (x - 1); i <= (x + 1); i++) {
            for (int j = (y - 1); j <= (y + 1); j++) {
                doSpread(c, i, j);
            }
        }
    }

    private void doSpread(Cell c, int i, int j) {
        try {
            if (board.get(i).get(j).getPlayer() != c.getPlayer() && board.get(i).get(j).getPlayer() != null) {
                board.get(i).get(j).setPlayer(c.getPlayer());
                spread(board.get(i).get(j));
            }
        } catch (Exception e) {
            //System.out.println("i :"+ i +" j : "+j);
        }

    }

    public void changePlayer() {
        player = Player.next(player);
        round++;
    }

    public boolean isCurrentPlayerTurn(Player player) {
        return this.player == player;
    }
}
