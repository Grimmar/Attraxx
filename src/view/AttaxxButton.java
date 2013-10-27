/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    }

    public Case getCaseModel() {
        return model;
    }

    public void redraw() {
        if (model.getPlayer() == null) {
            this.setBackground(Color.GRAY);
        } else {
            this.setBackground(model.getPlayer().getColor());
        }
    }
}
