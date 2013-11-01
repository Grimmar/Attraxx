package controller;

import javafx.scene.input.MouseEvent;
import model.AtaxxModel;
import model.Cell;
import view.Ataxx;
import view.AtaxxPiece;
import view.AtaxxTile;

public class AtaxxMousePressedHandler extends AtaxxAbstractHandler {

    public AtaxxMousePressedHandler(AtaxxModel model, Ataxx view) {
        super(model, view);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        AtaxxPiece piece = (AtaxxPiece) mouseEvent.getSource();
        if (model.isCurrentPlayerTurn(piece.getModel().getOwner())) {
            dragged = piece;
            AtaxxTile origin = piece.getOrigin();
            piece.startMove();
            for (AtaxxTile r : view.getTiles()) {
                if (r != origin && !r.getModel().isLocked()) {
                    if (r.getModel().isNear(origin.getModel())) {
                        if (r.getModel().getPiece() != null || !Cell.isCellAvailable(r.getModel())) {
                            r.setInvalidColor();
                        } else if (dragged.getOrigin().getModel().canMove(r.getModel())) {
                            r.setValidColor();
                        }
                    }
                }
            }
        }
    }
}
