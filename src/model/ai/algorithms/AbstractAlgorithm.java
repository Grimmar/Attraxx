package model.ai.algorithms;

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
        root = new TreeNode();
        computeTree(model, Owner.RED, root, 0);
        long duree = System.nanoTime() - start;
        System.out.println("Création de l'arbre : " + duree + " en ns");
    }

    private void computeTree(AtaxxModel model, Owner owner, Node node, int depth) {
        if (depth >= this.depth) {
            return;
        }
        int size = model.getBoardSize();
        List<TileModel> tiles = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                TileModel t = model.get(j, i);
                if (owner.equals(t.getOwner())) {
                    tiles.add(t);
                }
            }
        }
        for (TileModel start : tiles) {
            List<TileModel> possibleMoves = model.getPossibleMoves(start);
            for (TileModel end : possibleMoves) {
                Node n = new TreeNode(start, end);
                try {
                    AtaxxModel clone = (AtaxxModel) model.clone();
                    clone.move(clone.get(start.getPositionX(), start.getPositionY()),
                            clone.get(end.getPositionX(), end.getPositionY()));
                    if (this.depth == depth + 1) {
                        n.setValue(clone.getRedTokens() - clone.getBlueTokens());
                    } else {
                        computeTree(clone, owner.opposite(), n, depth + 1);
                    }
                } catch (IllegalAccessException e) {
                    //System.out.println(e.getMessage());
                }
                node.addSuccessor(n);
            }
        }
    }

    @Override
    public Node max(Node n1, Node n2) {
        if (n1 == null) {
          return n2;
        } else if (n2 == null) {
            return n1;
        }
        if (n1.getValue() > n2.getValue()) {
            return n1;
        }
        return n2;
    }

    @Override
    public Node min(Node n1, Node n2) {
        if (n1 == null) {
            return n2;
        } else if (n2 == null) {
            return n1;
        }
        if (n1.getValue() < n2.getValue()) {
            return n1;
        }
        return n2;
    }
}
