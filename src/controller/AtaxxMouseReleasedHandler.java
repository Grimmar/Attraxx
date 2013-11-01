package controller;

import javafx.scene.input.MouseEvent;
import model.AtaxxModel;
import model.TileModel;
import model.PieceModel;
import view.Ataxx;
import view.PieceView;
import view.TileView;

public class AtaxxMouseReleasedHandler extends AtaxxAbstractHandler {

    public AtaxxMouseReleasedHandler(AtaxxModel model, Ataxx view) {
        super(model, view);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (dragged != null) {
            TileModel originModel = dragged.getOrigin().getModel();
            for (TileView r : view.getTileViews()) {
                if (r != dragged.getOrigin() && !r.getModel().isLocked()) {
                    if (r.getModel().isNear(originModel))
                        r.clearColor();
                }
            }
            if (arrival == null || !TileModel.isCellAvailable(arrival.getModel())
                    || !originModel.canMove(arrival.getModel())) {
                dragged.cancelMove();
            } else {
                try {
                    model.move(originModel, arrival.getModel());
                    view.clearPieces();

                    for (TileView r : view.getTileViews()) {
                        PieceModel pieceModel = r.getModel().getPieceModel();
                        if (pieceModel != null) {
                            PieceView pieceViewRegion = view.makeAtaxxPiece(pieceModel, r);
                            view.addPiece(pieceViewRegion);
                            view.getPane().getChildren().add(pieceViewRegion);
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
