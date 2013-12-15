package model.ai.tree;

import java.util.List;
import model.TileModel;

public interface Node {

    boolean isLeaf();

    List<Node> getSuccessors();

    void addSuccessor(Node n);

    void setValue(int tokens);
    
    int getValue();
    
    Node getParent();
    
    boolean isMax();
    
    void setParent(Node parent);
    
    TileModel getStartingTile();
    
    TileModel getEndingTile();
}
