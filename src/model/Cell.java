/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Cell {

    private Position position;
    private Owner owner;
    private PropertyChangeSupport propertyChangeSupport;
    
    public Cell(int x, int y){
        position = new Position(x, y);
        owner = Owner.NONE;
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public int getPositionX() {
        return position.getX();
    }

    public int getPositionY() {
        return position.getY();
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        Owner oldOwner = this.owner;
        this.owner = owner;
        propertyChangeSupport.firePropertyChange("owner", oldOwner, owner);
    }

    public static boolean isCellAvailable(Cell end) {
        return end.getOwner() == Owner.NONE;
    }

    public void clear() {
        owner = Owner.NONE;
    }

    public boolean isNear(Cell c, int range) {
        return position.isNear(c.position, range);
    }

    public boolean canMove(Cell c){
        return position.canMove(c.position);
    }

    public void addPropertyChangeSupport(PropertyChangeListener ls){
        propertyChangeSupport.addPropertyChangeListener(ls);
    }
    public void removePropertyChangeSupport(PropertyChangeListener ls){
        propertyChangeSupport.removePropertyChangeListener(ls);
    }
    
    @Override
    public String toString() {
        if (owner == null) {
            return "N";
        }
        switch (owner) {
            case BLUE:
                return "P1";
            case RED:
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

        public boolean canMove(Position p){
            if(x == p.x || y == p.y){
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
