/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.Cell;
import model.Owner;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author David
 */
public class AtaxxPanel extends JPanel {

    private Cell model;

    public AtaxxPanel(final Cell cell) {
        this.model = cell;
        this.setPreferredSize(new Dimension(50, 50));
        createController();
    }

    private void createController() {
        model.addPropertyChangeSupport(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                repaint();
            }
        });
    }

    @Override
    public Border getBorder() {
        return new LineBorder(Color.BLACK);
    }

    @Override
    public Color getBackground() {
        if (model == null || Owner.NONE == model.getOwner()) {
            return super.getBackground();
        } else if (Owner.VOID == model.getOwner()) {
            return Color.BLACK;
        } else {
            return model.getOwner().getColor();
        }
    }

    public Cell getModel() {
        return model;
    }

    public Owner getOwner() {
        return model.getOwner();
    }
}
