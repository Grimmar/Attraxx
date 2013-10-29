package view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.scene.shape.Circle;
import model.Piece;

public class AtaxxPiece extends Circle {
    private Piece piece;
    private AtaxxRegion origin;
    private NumberBinding positionX = null;
    private NumberBinding positionY = null;

    public AtaxxPiece(Piece piece, AtaxxRegion r) {
        this.piece = piece;
        origin = r;
        /*Reflection reflection = new Reflection();
        reflection.setFraction(1);
        reflection.topOffsetProperty().bind(radiusProperty().multiply(-.9));
        setEffect(reflection);*/

        fillProperty().bind(piece.getOwner().colorProperty());
        positionX = Bindings.selectDouble(r.xProperty().add(r.widthProperty().divide(2)));
        positionY = Bindings.selectDouble(r.yProperty().add(r.widthProperty().divide(2)));
        centerXProperty().bind(positionX);
        centerYProperty().bind(positionY);
    }

    private void unbindCenter() {
        centerXProperty().unbind();
        centerYProperty().unbind();
    }

    public void startMoving() {
         unbindCenter();
    }

    public void move(double x,  double y) {
        unbindCenter();
        setCenterX(x);
        setCenterY(y);
    }

    public void cancel() {
        updatePosition(origin);
    }

   private void updatePosition(AtaxxRegion r) {
       positionX = Bindings.selectInteger(r.xProperty().add(r.widthProperty().divide(2)));
       positionY = Bindings.selectInteger(r.yProperty().add(r.widthProperty().divide(2)));
       centerXProperty().bind(positionX);
       centerYProperty().bind(positionY);
   }

    public Piece getModel() {
        return piece;
    }

    public AtaxxRegion getOrigin() {
        return origin;
    }
}
