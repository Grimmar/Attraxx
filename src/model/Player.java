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
    
    PLAYER1(Color.BLUE), PLAYER2(Color.RED);
    
    private Color color;
    
    Player(Color c){
        color = c;
    }
    
    public Color getColor(){
        return color;
    }
    
}
