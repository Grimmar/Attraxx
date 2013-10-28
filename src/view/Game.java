package view;

import controller.AtaxxMouseListener;
import model.AtaxxModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class Game {

    private static final int BOARD_SIZE = 7;
    private AtaxxModel model;
    private List<JPanel> ataxxPanels;
    private JFrame frame;

    public Game() {
        createModel();
        createView();
        createMenuActions();
        createAndInstallMenu();
        createController();
        placeComponents();
    }

    private void createModel() {
        model = AtaxxModel.getInstance();
        model.generate(BOARD_SIZE, AtaxxModel.TWO_TOKENS, 1);
    }

    private void createView() {
        frame = new JFrame("Ataxx");
        ataxxPanels = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                ataxxPanels.add(new AtaxxPanel(model.get(i, j)));
            }
        }
    }

    private void createAndInstallMenu() {
        JMenuBar menuBar = new JMenuBar();
        for (Menu m : Menu.values()) {
            JMenu menu = new JMenu(m.getName());

            List<Item> items = Menu.getItems(m);
             for (Item i : items) {
                 if (i == null) {
                    menu.addSeparator();
                 } else {
                    menu.add(new JMenuItem(i.getAction()));
                 }
             }
            menuBar.add(menu);
        }
        frame.setJMenuBar(menuBar);
    }

    private void createMenuActions() {
         Item.NEW_GAME.setAction(new AbstractAction() {
             @Override
             public void actionPerformed(ActionEvent e) {
                   System.out.println(Item.NEW_GAME.ordinal());
             }
         });
    }

    private void createController() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MouseListener listener = new AtaxxMouseListener(model);
        for (JPanel p : ataxxPanels) {
            p.addMouseListener(listener);
        }
    }

    private void placeComponents() {
        JPanel p = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        for (JPanel q : ataxxPanels) {
            p.add(q);
        }
        frame.add(p);
    }

    public void display() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game().display();
            }
        });
    }
}