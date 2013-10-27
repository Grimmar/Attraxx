/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import model.AttaxxModel;
import model.Case;

/**
 *
 * @author David
 */
public class Game {

    private static final int SIZE = 7;
    private AttaxxModel model;
    private JFrame frame;
    private Case start = null;
    private Case end = null;

    public void run() {
        initModel();
        init();
        initGame();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initGame() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(SIZE, SIZE));
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                AttaxxButton b = new AttaxxButton(model.getCase(i, j));
                b.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        AttaxxButton b = (AttaxxButton) e.getSource();
//                        System.out.println(b.getCaseModel());
                        if (b.getCaseModel().getPlayer() != null) {
                            if (start == null) {
                                start = b.getCaseModel();
                            }
                        }
                        if (start != null && b.getCaseModel().getPlayer() == null) {
                            end = b.getCaseModel();
                            System.out.println(end.getPosition());
                            System.out.println(end.getPlayer());
                            model.move(start, end);
                            b.redraw();
                            start = null;
                            end = null;
                        }
                    }
                });
                p.add(b);
            }
        }
        frame.add(p);
    }

    private void initModel() {
        model = new AttaxxModel();
        model.init(SIZE);
    }

    private void init() {
        frame = new JFrame("Attaxx");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game g = new Game();
                g.run();
            }
        });
    }
}
