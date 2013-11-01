/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class TileModel {
    private ObjectProperty<Position> position;
    private boolean locked;
    private PieceModel pieceModel;

    public TileModel(int x, int y){
        locked = false;
        positionProperty().set(new Position(x, y));
        pieceModel = null;
    }

    public ObjectProperty<Position> positionProperty() {
        if(this.position == null) { this.position = new SimpleObjectProperty<>(); }
        return this.position;
    }

    public int getPositionX() {
        return positionProperty().get().getX();
    }

    public int getPositionY() {
        return position.get().getY();
    }

    public static boolean isCellAvailable(TileModel cell) {
        return cell.pieceModel == null && !cell.isLocked();
    }

    public boolean isNear(TileModel c) {
        return isNear(c, 1) || isNear(c, 2);
    }

    public boolean isNear(TileModel c, int range) {
        return position.get().isNear(c.position.get(), range);
    }

    public boolean canMove(TileModel c){
        return position.get().canMove(c.position.get());
    }

    public boolean isLocked() {
        return locked;
    }

    public void lock() {
        locked = true;
    }

    public void addPiece(Owner o) {
        pieceModel = new PieceModel(o);
    }

    public PieceModel getPieceModel() {
        return pieceModel;
    }

    public void clear() {
        pieceModel = null;
    }

    public Owner getOwner() {
        return pieceModel != null ? pieceModel.getOwner() : null;
    }

    public void setOwner(Owner owner) {
        this.pieceModel.setOwner(owner);
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
            } else if(((x+1) == p.x && (y+1) == p.y)
                    || ((x+1) == p.x && (y-1) == p.y)
                    || ((x-1) == p.x && (y+1) == p.y)
                    || ((x-1) == p.x && (y-1) == p.y)) {
                return true;
            } else if(((x+2) == p.x && (y+2) == p.y)
                    || ((x-2) == p.x && (y+2) == p.y)
                    || ((x+2) == p.x && (y-2) == p.y)
                    || ((x-2) == p.x && (y-2) == p.y)){
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
