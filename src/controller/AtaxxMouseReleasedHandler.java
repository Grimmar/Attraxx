package controller;

import javafx.scene.input.MouseEvent;
import model.AtaxxModel;
import model.Owner;
import model.TileModel;
import view.Ataxx;
import view.component.TileView;

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

                    if (model.isGameOver()) {
                         System.out.println("fin");
                        //TODO Afficher message de fin et vainqueur
                    } else {
                        while(model.isGameVSComputer() && model.isCurrentPlayerTurn(Owner.RED)
                                && !model.isGameOver()) {
                            model.play();
                            view.refresh();
                        }
                    }
                } catch (IllegalAccessException ex) {
                }
            }
        }
        dragged = null;
        arrival = null;
    }

}
