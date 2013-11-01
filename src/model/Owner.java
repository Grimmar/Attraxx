package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;

/**
 *
 * @author David
 */
public enum Owner {
    BLUE(RadialGradient.valueOf("radial-gradient("
            +"center 50% 18%, radius 50%, reflect, blue, #0000CD 80%)")),
    RED(RadialGradient.valueOf("radial-gradient("
            +"center 50% 18%, radius 50%, reflect, red, #CD2626 80%)"));

    private ObjectProperty<Paint> color;

    Owner(Paint paint) {
        colorProperty().set(paint);
    }

    public Owner opposite() {
        return this == BLUE ? RED : BLUE;
    }

    public ObjectProperty<Paint> colorProperty() {
        if(this.color == null) { this.color = new SimpleObjectProperty<>(); }
        return this.color;
    }
}
