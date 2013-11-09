package model.ai;

import model.AtaxxModel;
import model.TileModel;
import model.ai.tree.Node;

public interface Algorithm {
    TileModel run(Node s);
    void buildTree(AtaxxModel model);

    Node getRoot();
}
