package controller;

import model.AtaxxModel;
import view.AtaxxPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AtaxxMouseListener implements MouseListener {

    private AtaxxModel model;
    private AtaxxPanel begin;
    private AtaxxPanel end;

    public AtaxxMouseListener(AtaxxModel model) {
        this.model = model;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        AtaxxPanel panel = (AtaxxPanel) e.getSource();
        if (begin == null && model.isCurrentPlayerTurn(panel.getModel().getOwner())) {
            begin = panel;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        AtaxxPanel panel = (AtaxxPanel) e.getSource();
        if (begin != null) {
            if (panel.getModel().getOwner() == null) {
                end = panel;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (begin != null && end != null){
            try {
                model.move(begin.getModel(), end.getModel());
                model.changePlayer();
            } catch (IllegalAccessException ex) {
            }
            end.repaint();
            begin.repaint();
            System.out.println("[" + model.getBlueTokens() + ", " + model.getRedTokens() + "]");
        }
        end = null;
        begin = null;
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
