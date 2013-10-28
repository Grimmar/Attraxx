/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;

/**
 *
 * @author David
 */
public enum Owner {

    //VOID is for holes
    BLUE, RED, NONE, VOID;

    public Color getColor() {
        return this == Owner.BLUE ? Color.BLUE : Color.RED;
    }

    public Owner opposite() {
        return this == BLUE ? RED : this == RED ? BLUE : NONE;
    }
}
