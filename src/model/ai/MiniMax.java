package model.ai;

import model.TileModel;
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
    public Node run(Node s) {
        Node n = null;
        if (s.getTile() == null) {
            for (Node succ : s.getSuccessors()) {
                if (n == null) {
                    n = miniMax(succ);
                } else {
                    n = Max(n, miniMax(succ));
                }
            }
        } else {
            n = miniMax(s);
        }

        while (n.getParent() != s) {
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
                t.setValue(-1);
                for (Node succ : n.getSuccessors()) {
                    t = Max(t, miniMax(succ));
                }
            } else {
                t.setValue(Integer.MAX_VALUE);
                for (Node succ : n.getSuccessors()) {
                    t = Min(t, miniMax(succ));
                }
            }
        }
        return t;
    }
}
