/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 *
 * @author David
 */
public enum Owner {

    BLUE(Color.BLUE), RED(Color.RED);

    private ObjectProperty<Paint> color;

    Owner(Color color) {
        colorProperty().set(color);
    }

    public Owner opposite() {
        return this == BLUE ? RED : BLUE;
    }

    public ObjectProperty<Paint> colorProperty() {
        if(this.color == null) { this.color = new SimpleObjectProperty<>(); }
        return this.color;
    }
}
