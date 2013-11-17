package model.ai;

import model.AtaxxModel;
import model.Owner;
import model.TileModel;
import model.ai.tree.Node;
import model.ai.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAlgorithm implements Algorithm {

    protected Node root;
    private int depth;

    public AbstractAlgorithm(int depth) {
        this.depth = depth;
        this.root = new TreeNode();
    }

    @Override
    public void buildTree(AtaxxModel model) {
        long start = System.nanoTime();
        computeTree(model, Owner.RED, root, 0);
        long duree = System.nanoTime() - start;
        System.out.println("temps de création de l'arbre : "+duree +" en ns");
    }

    private void computeTree(AtaxxModel model, Owner owner, Node node, int depth) {
        if (depth >= this.depth) {
            return;
        }
        int size = model.getBoardSize();
        List<TileModel> tiles = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                TileModel t = model.get(i, j);
                if (owner == t.getOwner()) {
                    tiles.add(t);
                }
            }
        }

        for (TileModel tile : tiles) {
            List<TileModel> possibleMoves = model.getPossibleMoves(tile);

            for (TileModel t : possibleMoves) {
                Node n = new TreeNode(tile, t);
                try {
                    AtaxxModel clone = (AtaxxModel) model.clone();
                    clone.move(clone.get(tile.getPositionX(), tile.getPositionY()),
                            clone.get(t.getPositionX(), t.getPositionY()));
                    if (depth + 1 == this.depth) {
                        n.setValue(clone.getRedTokens());
                    } else {
                        computeTree(clone, owner.opposite(), n, depth + 1);
                    }
                } catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
                n.setParent(node);
                node.addSuccessor(n);
            }

        }
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public Node Max(Node n1, Node n2) {
        if (n1.getValue() > n2.getValue()) {
            return n1;
        }
        return n2;
    }

    @Override
    public Node Min(Node n1, Node n2) {
        if (n1.getValue() < n2.getValue()) {
            return n1;
        }
        return n2;
    }
}
