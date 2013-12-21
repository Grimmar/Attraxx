/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ai.algorithms;

import java.util.List;
import model.ai.tree.Node;
import model.ai.tree.TreeNode;

/**
 *
 * @author David
 */
public class AlphaBeta extends AbstractAlgorithm {

    public AlphaBeta(int depth) {
        super(depth);
    }

    @Override
    protected Node runAlgorithm() {
        Node n = null;
        for (Node succ : root.getSuccessors()) {
            Node alpha = new TreeNode();
            Node beta = new TreeNode();
            alpha.setValue(Integer.MIN_VALUE);
            beta.setValue(Integer.MAX_VALUE);
            if (n == null) {
                n = AlphaBeta(succ, alpha, beta);
            } else {
               // if (n.isMax()) {
                    n = max(n, AlphaBeta(succ, alpha, beta));
                /*} else {
                    System.out.println("ici");
                    n = min(n, AlphaBeta(succ, alpha, beta));
                }*/
            }

        }
        while (root != n && root
                != n.getParent()) {
            n = n.getParent();
        }

        System.out.println(
                "Move " + n.getStartingTile().getPositionX() + " , " + n.getStartingTile().getPositionY() + " =>"
                + n.getEndingTile().getPositionX() + " , " + n.getEndingTile().getPositionY());
        return n;
    }

    private Node AlphaBeta(Node n, Node alpha, Node beta) {
        Node val = null;
        if (n.isLeaf()) {
            val = n;
        } else {
            int size = n.getSuccessors().size();
            List<Node> successors = n.getSuccessors();
            val = new TreeNode();
            if (n.isMax()) {
                int i = 0;
                val.setValue(Integer.MIN_VALUE);
                while (alpha.getValue() < beta.getValue() && i < size) {
                    val = max(val, AlphaBeta(successors.get(i), alpha, beta));
                    if(val.getValue() >= beta.getValue())
                         return val;
                    alpha = max(alpha, val);
                    i++;
                }
            } else {
                int i = 0;
                val.setValue(Integer.MAX_VALUE);
                while (alpha.getValue() < beta.getValue() && i < size) {
                    val = min(val, AlphaBeta(successors.get(i), alpha, beta));
                    if(alpha.getValue() >= val.getValue()){
                        return val;
                    }
                    beta = min(beta, val);
                    i++;
                }
                val = beta;
            }
        }
        return val;
    }

}
