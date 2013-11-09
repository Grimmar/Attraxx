package controller;

import javafx.scene.input.MouseEvent;
import model.AtaxxModel;
import view.Ataxx;
import view.TileView;

public class AtaxxMouseDraggedHandler extends AtaxxAbstractHandler {

    public AtaxxMouseDraggedHandler(AtaxxModel model, Ataxx view) {
        super(model, view);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        if (dragged != null && x > 0 && y > 0
                && x < view.getSceneWidth() && y < view.getSceneHeight()) {
            dragged.move(x, y);
        }
        for (TileView r : view.getTileViews()) {
            if (r.contains(x, y)) {
                arrival = r;
            }
        }
    }
}
