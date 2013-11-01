package view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.PieceModel;

public class PieceView extends Circle {
    private PieceModel pieceModel;
    private TileView origin;
    private NumberBinding positionX = null;
    private NumberBinding positionY = null;

    public PieceView(PieceModel pieceModel, TileView r) {
        this.pieceModel = pieceModel;
        origin = r;

        fillProperty().bind(pieceModel.getOwner().colorProperty());
        positionX = Bindings.selectDouble(r.xProperty().add(r.widthProperty().divide(2)));
        positionY = Bindings.selectDouble(r.yProperty().add(r.widthProperty().divide(2)));
        centerXProperty().bind(positionX);
        centerYProperty().bind(positionY);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(4.0f);
        ds.setOffsetX(4.0f);
        ds.setColor(Color.BLACK);

        setEffect(ds);
    }

    public void startMove() {
         unbindCenter();
    }

    public void move(double x,  double y) {
        unbindCenter();
        setCenterX(x);
        setCenterY(y);
    }

    public void cancelMove() {
        positionX = Bindings.selectInteger(origin.xProperty().add(origin.widthProperty().divide(2)));
        positionY = Bindings.selectInteger(origin.yProperty().add(origin.widthProperty().divide(2)));
        centerXProperty().bind(positionX);
        centerYProperty().bind(positionY);
    }

    private void unbindCenter() {
        centerXProperty().unbind();
        centerYProperty().unbind();
    }

    public PieceModel getModel() {
        return pieceModel;
    }

    public TileView getOrigin() {
        return origin;
    }
}
