/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author David
 */
public class Position {
    
    private int x;
    private int y;
    
    
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
    public boolean isNear(Position p, int range){
        if(Math.abs(x - p.x) <= range && Math.abs(y - p.y) <= range){
            return true;
        }
        return false;
    }

    public boolean isPosibleMove(Position p){
        if(x == p.x ||y == p.y){
            return true;
        }
        if((x+2) == p.x && (y+2) == p.y){
            return true;
        }
        if((x-2) == p.x && (y+2) == p.y){
            return true;
        }
        if((x+2) == p.x && (y-2) == p.y){
            return true;
        }
        if((x-2) == p.x && (y-2) == p.y){
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return "Position{" + "x=" + x + ", y=" + y + '}';
    }
    
    
}
