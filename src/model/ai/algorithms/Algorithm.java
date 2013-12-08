package model.ai.algorithms;

import model.AtaxxModel;

import model.ai.tree.Node;

public interface Algorithm {
    
    Node run(Node s);
    
    void buildTree(AtaxxModel model);

    Node getRoot();
    
    Node Max(Node n1, Node n2);
    
    Node Min(Node n1, Node n2);
    
}
