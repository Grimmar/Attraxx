/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.component;

import javafx.scene.effect.Light;
import javafx.scene.effect.LightingBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.TileModel;

public class TileView extends Rectangle {

    private final TileModel model;
    private static final Color normal = Color.WHITESMOKE;
    private static final Color locked = Color.BLACK;
    private static final Color valid = Color.GREEN;
    private static final Color invalid = Color.RED;

    public TileView(TileModel c) {
        this.model = c;
        if (model.isLocked()) {
            setFill(locked);
        } else {
            setFill(normal);
        }
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135);
        light.setElevation(30);
        setEffect(LightingBuilder.create().light(light).build());
    }

    public TileModel getModel() {
        return model;
    }

    public void setValidColor() {
        if (!model.isLocked()) {
            setFill(valid);
        }
    }

    public void setInvalidColor() {
        if (!model.isLocked()) {
            setFill(invalid);
        }
    }

    public void clearColor() {
        if (!model.isLocked()) {
            setFill(normal);
        }
    }

}
