package model.ai.algorithms;

import model.ai.tree.Node;
import model.ai.tree.TreeNode;

/**
 * Created with IntelliJ IDEA. User: Quentin Date: 08/11/13 Time: 15:29 To
 * change this template use File | Settings | File Templates.
 */
public class MiniMax extends AbstractAlgorithm {

    public MiniMax(int depth) {
        super(depth);
    }

    @Override
    public Node runThread() {
        Node n = null;

        for (Node succ : root.getSuccessors()) {
            if (n == null) {
                n = miniMax(succ);
            } else {
                n = max(n, miniMax(succ));
            }
        }
        while (root != n && root != n.getParent()) {
            n = n.getParent();
        }

        System.out.println("Move " + n.getTile().getPositionX() + " , " + n.getTile().getPositionY() + " =>"
                + n.getTileEnd().getPositionX() + " , " + n.getTileEnd().getPositionY());
        return n;
    }

    private Node miniMax(Node n) {
        Node t;
        if (n.isLeaf()) {
            return n;
        } else {
            t = new TreeNode();
            if (n.isMax()) {
                t.setValue(Integer.MIN_VALUE);
                for (Node succ : n.getSuccessors()) {
                    t = max(t, miniMax(succ));
                }
            } else {
                t.setValue(Integer.MAX_VALUE);
                for (Node succ : n.getSuccessors()) {
                    t = min(t, miniMax(succ));
                }
            }
        }
        return t;
    }
}
