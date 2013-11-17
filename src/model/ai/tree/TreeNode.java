package model.ai.tree;

import model.TileModel;

import java.util.LinkedList;
import java.util.List;
import model.Owner;

public class TreeNode implements Node {

    private TileModel tile;
    private TileModel tileEnd;
    private List<Node> children;
    private int value;
    private Node parent;

    public TreeNode() {
        this(null, null);
    }

    public TreeNode(TileModel tile, TileModel end) {
        this.tile = tile;
        this.tileEnd = end;
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
        return Owner.RED == tile.getOwner();
    }

    @Override
    public TileModel getTile() {
        return tile;
    }

    @Override
    public TileModel getTileEnd() {
        return tileEnd;
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
        return sb.toString();
    }

    /*public void print(String space) {
     if (tile != null) {
     System.out.println(space + " Position depart(" + tile.getPositionX() + " , " + tile.getPositionY() + ")");
     System.out.println(space + " Position arrivé(" + tileEnd.getPositionX() + " , " + tileEnd.getPositionY() + ")");
     System.out.println(space + " OWNER "+ tile.getOwner());
     }
     space += "  ";
     for (Node n : this.getSuccessors()) {
            
     n.print(space);
     }

     }*/
}
