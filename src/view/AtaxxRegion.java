/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.effect.Light;
import javafx.scene.effect.LightingBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Cell;

public class AtaxxRegion extends Rectangle {

    private final Cell model;
    private static final Color normal = Color.WHITESMOKE;
    private static final Color locked = Color.BLACK;
    private static final Color valid = Color.GREEN;
    private static final Color invalid = Color.RED;
    public AtaxxRegion(Cell c) {
        if (c.isLocked()) {
            setFill(locked);
        } else {
            setFill(normal);
        }
        this.model = c;
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135);
        light.setElevation(30);

        setEffect(LightingBuilder.create().light(light).build());
    }

    public Cell getModel() {
        return model;
    }

    public void validate() {
        if (!model.isLocked()) {
            setFill(valid);
        }
    }

    public void invalidate() {
        if (!model.isLocked()) {
            setFill(invalid);
        }
    }

    public void clear() {
        if (!model.isLocked()) {
            setFill(normal);
        }
    }
}
