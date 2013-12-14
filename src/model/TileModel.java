package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class TileModel implements Cloneable {
    private ObjectProperty<Position> position;
    private PieceModel pieceModel;
    private boolean locked;

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

    public boolean canMoveTo(TileModel c){
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

    @Override
    public Object clone() {
        TileModel o = new TileModel(getPositionX(), getPositionY());
        if (pieceModel != null) {
            o.pieceModel = (PieceModel) pieceModel.clone();
        }
        o.locked = locked;
        return o;
    }
}
