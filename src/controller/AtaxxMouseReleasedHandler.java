package controller;

import javafx.scene.input.MouseEvent;
import model.AtaxxModel;
import model.TileModel;
import view.Ataxx;
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
                if (!r.getModel().isLocked()) {
                    r.clearColor();
                }
            }
            if (arrival == null || !TileModel.isCellAvailable(arrival.getModel())
                    || !model.canMoveTo(originModel, arrival.getModel())
                    || !originModel.isNear(arrival.getModel())) {
                dragged.cancelMove();
            } else {
                try {
                    /*System.out.println("Move bleu : "+originModel.getPositionX() + " , "+originModel.getPositionY()
                    +" => "+arrival.getModel().getPositionX()+" , "+arrival.getModel().getPositionY());*/
                    model.move(originModel, arrival.getModel());
                    view.refresh();
                    model.changePlayer();
                    view.refresh();
                    if (model.isGameOver()) {
                        //TODO Afficher message de fin et vainqueur
                    } else {
                        //TODO if !canPlay switch owner
                    }
                } catch (IllegalAccessException ex) {
                }
            }
        }
        dragged = null;
        arrival = null;
    }

}
