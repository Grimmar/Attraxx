/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import model.Case;

/**
 *
 * @author David
 */
public class AttaxxButton extends JButton {

    private Case model;

    public AttaxxButton(final Case model) {
        this.model = model;
        if (model.getPlayer() != null) {
            this.setBackground(model.getPlayer().getColor());
        }
        this.setPreferredSize(new Dimension(50, 50));

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(model.getPosition());

            }
        });
        
        model.addPropertyChangeSuppor(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("fdsf"+evt.getNewValue()); 
                System.out.println(evt.getOldValue()); 
                redraw();
            }
        });

    }

    public Case getCaseModel() {
        return model;
    }

    public void redraw() {
        if (model.getPlayer() == null) {
            this.setBackground(null);
        } else {
            this.setBackground(model.getPlayer().getColor());
        }
    }
}
