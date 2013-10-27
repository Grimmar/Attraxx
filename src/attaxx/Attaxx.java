package attaxx;


import model.*;

/**
 *
 * @author David
 */
public class Attaxx  {
    
    public static void main(String[] args) {
        AttaxxModel m = new AttaxxModel();
        m.init(7);
        m.print();
        Case c1 = new Case(0,0);
        c1.setPlayer(Player.PLAYER1);
        Case c2 = new Case(1,1);
        m.move(c1, c2);
        System.out.println("======================");
        m.print();
    }
}
