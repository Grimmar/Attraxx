package model.ai.tree;

import model.TileModel;

import java.util.LinkedList;
import java.util.List;

public class TreeNode implements Node {

    private TileModel tile;
    private List<Node> children;
    private int value;

    public TreeNode() {
        this(null);
    }

    public TreeNode(TileModel tile) {
        this.tile = tile;
        children = new LinkedList<>();
        value = -1;
    }

    @Override
    public List<Node> getSuccessors() {
        return children;
    }

    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public void addSuccessor(Node n) {
        if (n == null) {
            throw new IllegalArgumentException("Invalid node");
        }
        children.add(n);
    }

    @Override
    public void setValue(int tokens) {
        value = tokens;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Value ").append(value).append("\n");
        if (tile != null) {
            sb.append("Position (").append(tile.getPositionX()).append(" ; ").append(tile.getPositionY()).append(")");
            sb.append("Owner ").append(tile.getOwner());
        }
        for (Node d : children) {
            sb.append(" ");
            sb.append(d.toString());
        }
        //sb.append("\n");
        return sb.toString();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
