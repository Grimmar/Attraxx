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
                    model.move(originModel, arrival.getModel());
                    view.refresh();
                    model.changePlayer();
                    if (model.isGameVSComputer()) {
                          model.play();
                    }
                    view.refresh();
                    if (model.isGameOver()) {
                        System.out.println("fin");
                        //TODO Afficher message de fin et vainqueur
                    } else if (!model.canPlay()){
                         model.changePlayer();
                        //TODO display winner
                    }
                } catch (IllegalAccessException ex) {
                }
            }
        }
        dragged = null;
        arrival = null;
    }

}
