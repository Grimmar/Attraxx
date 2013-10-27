/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Cell {

    private Position position;
    private Player player;
    private PropertyChangeSupport propertyChangeSupport;
    
    public Cell(int x, int y){
        position = new Position(x, y);
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public int getPositionX() {
        return position.getX();
    }

    public int getPositionY() {
        return position.getY();
    }

    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        Player oldPlayer = this.player;
        this.player = player;
        propertyChangeSupport.firePropertyChange("player", oldPlayer, player);
    }

    public boolean isNear(Cell c, int range) {
        return position.isNear(c.position, range);
    }

    public boolean isMovePossible(Cell c){
        return position.isPossibleMove(c.position);
    }
    public void addPropertyChangeSupport(PropertyChangeListener ls){
        propertyChangeSupport.addPropertyChangeListener(ls);
    }
    public void removePropertyChangeSupport(PropertyChangeListener ls){
        propertyChangeSupport.removePropertyChangeListener(ls);
    }
    
    @Override
    public String toString() {
        if (player == null) {
            return "N";
        }
        switch (player) {
            case FIRST_PLAYER:
                return "P1";
            case SECOND_PLAYER:
                return "P2";
        }
        return "";
    }

    private class Position {

        private final int x;
        private final int y;

        public Position(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean isNear(Position p, int range){
            if(Math.abs(x - p.x) <= range && Math.abs(y - p.y) <= range){
                return true;
            }
            return false;
        }

        public boolean isPossibleMove(Position p){
            if(x == p.x ||y == p.y){
                return true;
            }
            if((x+2) == p.x && (y+2) == p.y){
                return true;
            }
            if((x-2) == p.x && (y+2) == p.y){
                return true;
            }
            if((x+2) == p.x && (y-2) == p.y){
                return true;
            }
            if((x-2) == p.x && (y-2) == p.y){
                return true;
            }
            return false;
        }
        @Override
        public String toString() {
            return "Position{" + "x=" + x + ", y=" + y + '}';
        }
    }
}
