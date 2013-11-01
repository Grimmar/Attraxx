package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.AtaxxModel;
import view.Ataxx;
import view.AtaxxPiece;
import view.AtaxxTile;

public abstract class AtaxxAbstractHandler implements EventHandler<MouseEvent> {

    protected static AtaxxPiece dragged;
    protected static AtaxxTile arrival;
    protected AtaxxModel model;
    protected Ataxx view;

    public AtaxxAbstractHandler(AtaxxModel model, Ataxx view) {
        this.model = model;
        this.view = view;
    }

}
