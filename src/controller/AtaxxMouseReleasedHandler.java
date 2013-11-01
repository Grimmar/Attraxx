package controller;

import javafx.scene.input.MouseEvent;
import model.AtaxxModel;
import model.Cell;
import model.Piece;
import view.Ataxx;
import view.AtaxxPiece;
import view.AtaxxTile;

public class AtaxxMouseReleasedHandler extends AtaxxAbstractHandler {

    public AtaxxMouseReleasedHandler(AtaxxModel model, Ataxx view) {
        super(model, view);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (dragged != null) {
            Cell originModel = dragged.getOrigin().getModel();
            for (AtaxxTile r : view.getTiles()) {
                if (r != dragged.getOrigin() && !r.getModel().isLocked()) {
                    if (r.getModel().isNear(originModel))
                        r.clearColor();
                }
            }
            if (arrival == null || !Cell.isCellAvailable(arrival.getModel())
                    || !originModel.canMove(arrival.getModel())) {
                dragged.cancelMove();
            } else {
                try {
                    model.move(originModel, arrival.getModel());
                    view.clearPieces();

                    for (AtaxxTile r : view.getTiles()) {
                        Piece piece = r.getModel().getPiece();
                        if (piece != null) {
                            AtaxxPiece pieceRegion = view.makeAtaxxPiece(piece, r);
                            view.addPiece(pieceRegion);
                            view.getPane().getChildren().add(pieceRegion);
                        }

                    }
                    model.changePlayer();
                } catch (IllegalAccessException ex) {
                }
            }
        }
        dragged = null;
        arrival = null;
    }

}
