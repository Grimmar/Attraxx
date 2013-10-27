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
public enum Player {
    
    FIRST_PLAYER(Color.BLUE), SECOND_PLAYER(Color.RED);
    
    private Color color;
    Player(Color c){
        color = c;
    }
    
    public Color getColor(){
        return color;
    }

    public static Player next(Player p) {
        switch(p) {
            case FIRST_PLAYER:
                return SECOND_PLAYER;
            case SECOND_PLAYER:
                return FIRST_PLAYER;
        }
        return null;
    }
}
