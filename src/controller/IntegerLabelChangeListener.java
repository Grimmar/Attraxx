package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;

public class IntegerLabelChangeListener implements ChangeListener<Number> {

    private final Label label;

    public IntegerLabelChangeListener(Label label) {
        this.label = label;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
        label.setText(observableValue.getValue().toString());
    }
}
