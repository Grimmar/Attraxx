/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ai.algorithms;

import model.ai.tree.Node;
import model.ai.tree.TreeNode;

/**
 *
 * @author David
 */
public class Negamax extends AbstractAlgorithm {

    public Negamax(int depth) {
        super(depth);
    }

    @Override
    protected Node runAlgorithm() {
        Node n = null;

        for (Node succ : root.getSuccessors()) {
            if (n == null) {
                n = Negamax(succ);
            } else {
                System.out.println(n.isMax());
                n = max(n, Negamax(succ));
            }
        }
        while (root != n && root != n.getParent()) {
            n = n.getParent();
        }

        System.out.println("Move " + n.getStartingTile().getPositionX() + " , " + n.getStartingTile().getPositionY() + " =>"
                + n.getEndingTile().getPositionX() + " , " + n.getEndingTile().getPositionY());
        return n;
    }

    private Node Negamax(Node n) {
        if(n.isLeaf()){
            return n;
        } else {
            Node bestValue = new TreeNode();
            bestValue.setValue(Integer.MIN_VALUE);
            Node val;
            for(Node succ : n.getSuccessors()){
                val = Negamax(succ);
                val.setValue(-val.getValue());
                bestValue = max(bestValue, val);
            }
            return bestValue;
        }
    }

}
