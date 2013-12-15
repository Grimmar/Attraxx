package model.ai.algorithms;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import model.AtaxxModel;
import model.Owner;
import model.TileModel;
import model.ai.tree.Node;
import model.ai.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAlgorithm implements Algorithm {

    private Service<Void> service;
    private AtaxxModel model;
    protected Node root;
    private int depth;
    private Node resultNode;

    protected abstract Node runThread();

    public AbstractAlgorithm(int depth) {
        this.model = AtaxxModel.getInstance();
        this.depth = depth;
        service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override protected Void call() throws Exception {
                        buildTree();
                        resultNode = runThread();
                        TileModel start = resultNode.getTile();
                        TileModel end = resultNode.getTileEnd();
                        Thread.sleep(300);
                        try {
                            model.move(model.get(start.getPositionX(), start.getPositionY()),
                                    model.get(end.getPositionX(), end.getPositionY()));
                        } catch (IllegalAccessException ex) {
                            System.out.println("Problème lors du déplacement de la pièce en "
                                    + end.getPositionX() + " : " + end.getPositionY());
                        }
                        cancel();
                        return null;
                    }
                };
            }
        };
    }

    public ReadOnlyObjectProperty<Worker.State> stateProperty() {
        return service.stateProperty();
    }

    @Override
    public void start() {
        service.restart();
    }

    private void buildTree() {
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

    protected Node max(Node n1, Node n2) {
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

    protected Node min(Node n1, Node n2) {
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
