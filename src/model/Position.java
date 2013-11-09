package model;

public class Position {

    private final int x;
    private final int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isNear(Position p, int range){
        if(Math.abs(x - p.x) <= range && Math.abs(y - p.y) <= range){
            return true;
        }
        return false;
    }

    public boolean canMove(Position p){
        if(x == p.x || y == p.y){
            return true;
        } else if(((x+1) == p.x && (y+1) == p.y)
                || ((x+1) == p.x && (y-1) == p.y)
                || ((x-1) == p.x && (y+1) == p.y)
                || ((x-1) == p.x && (y-1) == p.y)) {
            return true;
        } else if(((x+2) == p.x && (y+2) == p.y)
                || ((x-2) == p.x && (y+2) == p.y)
                || ((x+2) == p.x && (y-2) == p.y)
                || ((x-2) == p.x && (y-2) == p.y)){
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return "Position{" + "x=" + x + ", y=" + y + '}';
    }
}
