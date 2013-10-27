package attaxx;


import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
            m.move(c1, c2);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Attaxx.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("======================");
        m.print();
    }
}