/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author David
 */
public class Case {

    private Position position;
    private Player player;
    
    public Case(int x, int y){
        position = new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isNear(Case c, int range) {
        return position.isNear(c.getPosition(), range);
    }

    @Override
    public String toString() {
        String chaine = "";
        if (player == null) {
            return "N";
        }
        switch (player) {
            case PLAYER1:
                chaine = "P1";
                break;
            case PLAYER2:
                chaine = "P2";
                break;
        }
        return chaine;
    }
}
