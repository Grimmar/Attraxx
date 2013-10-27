package controller;

import model.AtaxxModel;
import model.Player;
import view.AtaxxPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class AtaxxMouseListener implements MouseListener, MouseMotionListener {

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
        Player player = panel.getPlayer();
        if (player != null && begin == null && model.isCurrentPlayerTurn(player)) {
            begin = panel;
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
            end = null;
            begin.repaint();
            begin = null;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        AtaxxPanel panel = (AtaxxPanel) e.getSource();
        if (begin != null) {
            if (panel.getPlayer() == null) {
                end = panel;
            } else {
                begin.repaint();
                begin = null;
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
