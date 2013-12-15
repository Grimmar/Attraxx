package model.ai.tree;

import model.Owner;
import model.TileModel;

import java.util.LinkedList;
import java.util.List;

public class TreeNode implements Node {

    private TileModel start;
    private TileModel end;
    private List<Node> children;
    private int value;
    private Node parent;

    public TreeNode() {
        this(null, null);
    }

    public TreeNode(TileModel start, TileModel end) {
        this.start = start;
        this.end = end;
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
        n.setParent(this);
    }

    @Override
    public void setValue(int tokens) {
        value = tokens;
    }

    @Override
    public Node getParent() {
        return this.parent;
    }

    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public boolean isMax() {
        return Owner.RED == start.getOwner();
    }

    @Override
    public TileModel getStartingTile() {
        return start;
    }

    @Override
    public TileModel getEndingTile() {
        return end;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Value ").append(value).append("\n");
        if (start != null) {
            sb.append("Position (").append(start.getPositionX()).append(" ; ").append(start.getPositionY()).append(")");
            sb.append("Owner ").append(start.getOwner());
        }
        for (Node d : children) {
            sb.append(" ");
            sb.append(d.toString());
        }
        return sb.toString();
    }
}
