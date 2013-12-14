package model.ai.algorithms;

import model.AtaxxModel;

import model.ai.tree.Node;

public interface Algorithm {
    
    Node run();
    
    void buildTree(AtaxxModel model);

    Node max(Node n1, Node n2);
    
    Node min(Node n1, Node n2);
    
}
