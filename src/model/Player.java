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
    
    PLAYER1(Color.BLUE, 1), PLAYER2(Color.RED,2);
    
    private Color color;
    private int order;
    
    Player(Color c, int p){
        color = c;
        order = p;
    }
    
    public Color getColor(){
        return color;
    }
    
    public int getOrder(){
        return order;
    }
    
}
