/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.Cell;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 *
 * @author David
 */
public class AtaxxPanel extends JPanel {

    private Cell model;

    public AtaxxPanel(final Cell cell) {
        this.model = cell;
        this.setPreferredSize(new Dimension(50, 50));
    }

    @Override
    public Border getBorder() {
        return new LineBorder(Color.BLACK);
    }

    @Override
    public Color getBackground() {
        if (model != null && model.isLocked()) {
            return Color.BLACK;
        }
        return super.getBackground();
    }

    public Cell getModel() {
        return model;
    }
}
