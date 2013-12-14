package controller;

import javafx.scene.input.MouseEvent;
import model.AtaxxModel;
import model.TileModel;
import view.Ataxx;
import view.component.PieceView;
import view.component.TileView;

public class AtaxxMousePressedHandler extends AtaxxAbstractHandler {

    public AtaxxMousePressedHandler(AtaxxModel model, Ataxx view) {
        super(model, view);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        PieceView pieceView = (PieceView) mouseEvent.getSource();
        if (model.isCurrentPlayerTurn(pieceView.getModel().getOwner())) {
            dragged = pieceView;
            TileView origin = dragged.getOrigin();
            dragged.startMove();
            for (TileView tileView : view.getTileViews()) {
                if (tileView != origin) {
                    if (model.isMoveValid(origin.getModel(), tileView.getModel())) {
                        if (TileModel.isCellAvailable(tileView.getModel())) {
                            tileView.setValidColor();
                        }
                    }
                }
            }
        }
    }
}
