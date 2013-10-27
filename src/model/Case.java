/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author David
 */
public class Case {

    private Position position;
    private Player player;
    private PropertyChangeSupport pcs;
    
    public Case(int x, int y){
        position = new Position(x, y);
        pcs = new PropertyChangeSupport(this);
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
        Player oldP = this.player;
        this.player = player;
        pcs.firePropertyChange("player", oldP, player);
    }

    public boolean isNear(Case c, int range) {
        return position.isNear(c.getPosition(), range);
    }

    public void addPropertyChangeSuppor(PropertyChangeListener ls){
        pcs.addPropertyChangeListener(ls);
    }
    public void removePropertyChangeSuppor(PropertyChangeListener ls){
        pcs.removePropertyChangeListener(ls);
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
