package model.ai.algorithms;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.concurrent.Worker;

public interface Algorithm {
    void start();
    ReadOnlyObjectProperty<Worker.State> stateProperty();
}
