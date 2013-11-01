package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PieceModel {
    private ObjectProperty<Owner> owner;

    public PieceModel(Owner o) {
        setOwner(o);
    }
    public ObjectProperty<Owner> ownerProperty() {
        if(this.owner == null) { this.owner = new SimpleObjectProperty<>(); }
        return this.owner;
    }
    public final Owner getOwner() { return this.ownerProperty().get(); }
    public final void setOwner(Owner o) { this.ownerProperty().set(o); }
}
