package controller;

import javafx.scene.input.MouseEvent;
import model.AtaxxModel;
import model.TileModel;
import view.Ataxx;
import view.PieceView;
import view.TileView;

public class AtaxxMousePressedHandler extends AtaxxAbstractHandler {

    public AtaxxMousePressedHandler(AtaxxModel model, Ataxx view) {
        super(model, view);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        PieceView pieceView = (PieceView) mouseEvent.getSource();
        if (model.isCurrentPlayerTurn(pieceView.getModel().getOwner())) {
            dragged = pieceView;
            TileView origin = pieceView.getOrigin();
            pieceView.startMove();
            for (TileView r : view.getTileViews()) {
                if (r != origin) {
                    if (r.getModel().isNear(origin.getModel()) && !r.getModel().isLocked()) {
                        boolean canMoveTo = model.canMoveTo(dragged.getOrigin().getModel(), r.getModel());
                        if ((r.getModel().getPieceModel() != null || !TileModel.isCellAvailable(r.getModel()))
                                && canMoveTo) {
                            r.setInvalidColor();
                        } else if (canMoveTo) {
                            r.setValidColor();
                        }
                    }
                }
            }
        }
    }
}
