package model.ai;

import model.AtaxxModel;
import model.Owner;
import model.TileModel;
import model.ai.tree.Node;
import model.ai.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAlgorithm implements Algorithm {
    private Node root;
    private int depth;

    public AbstractAlgorithm(int depth) {
        this.depth = depth;
        this.root = new TreeNode();
    }

    @Override
    public void buildTree(AtaxxModel model) {
        computeTree(model, Owner.BLUE, root, 1);
    }

    private void computeTree(AtaxxModel model, Owner owner, Node node, int depth) {
        System.out.println("depth:" + depth);
        model.print();
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
                Node n = new TreeNode(t);
                try {
                    AtaxxModel clone = (AtaxxModel) model.clone();
                    clone.move(tile, t);
                    computeTree(clone, owner.opposite(), n, depth + 1);
                    if (depth + 1 == this.depth) {
                        n.setValue(clone.getRedTokens());
                    }
                } catch (IllegalAccessException e) {
                }
                node.addSuccessor(n);
            }
        }
    }

    public Node getRoot() {
        return root;
    }
}
