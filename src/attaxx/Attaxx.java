package attaxx;


import model.AtaxxModel;
import model.Cell;
import model.Owner;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class Attaxx  {
    
    public static void main(String[] args) {
        AtaxxModel m = AtaxxModel.getInstance();
        m.generate(7);
        m.print();
        Cell c1 = new Cell(0,0);
        c1.setOwner(Owner.BLUE);
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