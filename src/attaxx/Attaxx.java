package attaxx;


import model.AtaxxModel;
import model.Cell;
import model.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class Attaxx  {
    
    public static void main(String[] args) {
        AtaxxModel m = new AtaxxModel();
        m.generate(7);
        m.print();
        Cell c1 = new Cell(0,0);
        c1.setPlayer(Player.FIRST_PLAYER);
        Cell c2 = new Cell(1,1);
        try {
            m.move(c1, c2);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Attaxx.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("======================");
        m.print();
    }
}