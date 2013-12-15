package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import model.AtaxxModel;
import model.Owner;
import view.Ataxx;

/**
 * Created with IntelliJ IDEA.
 * User: Quentin
 * Date: 15/12/13
 * Time: 02:35
 * To change this template use File | Settings | File Templates.
 */
public class AlgorithmChangeListener implements ChangeListener<Worker.State> {

    private final AtaxxModel model;
    private final Ataxx view;

    public AlgorithmChangeListener(AtaxxModel model, Ataxx view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldState, Worker.State newState) {
        if (Worker.State.SUCCEEDED == observableValue.getValue()
                || Worker.State.CANCELLED == observableValue.getValue()) {
            view.refresh();
            if (model.isGameVSComputer() && model.isCurrentPlayerTurn(Owner.RED)
                    && !model.isGameOver()) {
                model.play();
                view.refresh();
            }
            if (model.isGameOver()) {
                view.displayWinner();
            }
            view.refresh();
        }
    }
}
