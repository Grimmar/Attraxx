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
public class AttaxxModel {

    private List<List<Case>> shelf;

    public AttaxxModel() {
        shelf = new ArrayList<List<Case>>();
    }

    public void init(int size) {
        shelf.clear();
        for (int i = 0; i < size; i++) {
            shelf.add(new ArrayList<Case>());
            for (int j = 0; j < size; j++) {
                Case c = new Case(i, j);
                if (i == 0 && j == 0) {
                    c.setPlayer(Player.PLAYER1);
                }
                if (i == size - 1 && j == size - 1) {
                    c.setPlayer(Player.PLAYER2);
                }
                shelf.get(i).add(c);
            }
        }
    }

    public void print() {
        for (List<Case> lc : shelf) {
            for (Case c : lc) {
                System.out.print(c + "   ");
            }
            System.out.println();
        }
    }

    public Case getCase(int x, int y) {
        if (x < 0 && x >= shelf.size() && y < 0 && y >= shelf.size()) {
            throw new IllegalArgumentException();
        }
        return shelf.get(x).get(y);
    }

    public void move(Case start, Case end) {
        Position pEnd = end.getPosition();
        if (start.isNear(end, 1)) {
            shelf.get(pEnd.getX()).get(pEnd.getY()).setPlayer(start.getPlayer());
            end.setPlayer(start.getPlayer());
            spread(end);
        } else {
            if (start.isNear(end, 2)) {
                Position pStart = start.getPosition();
                shelf.get(pEnd.getX()).get(pEnd.getY()).setPlayer(start.getPlayer());
                shelf.get(pStart.getX()).get(pStart.getY()).setPlayer(null);
                spread(shelf.get(pEnd.getX()).get(pEnd.getY()));
            }
        }
    }

    private void spread(Case c) {
        Position p = c.getPosition();
        int x = p.getX();
        int y = p.getY();

        for (int i = (x - 1); i <= (x + 1); i++) {
            System.out.println("i :" + i);
            for (int j = (y - 1); j <= (y + 1); j++) {
                doSpread(c, i, j);
            }
        }
    }

    private void doSpread(Case c, int i, int j) {
        try {
            if (shelf.get(i).get(j).getPlayer() != c.getPlayer() && shelf.get(i).get(j).getPlayer() != null) {
                shelf.get(i).get(j).setPlayer(c.getPlayer());
                spread(shelf.get(i).get(j));
            }
        } catch (Exception e) {
            //System.out.println("i :"+ i +" j : "+j);
        }

    }
}
