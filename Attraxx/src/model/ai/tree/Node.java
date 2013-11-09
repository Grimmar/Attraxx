package model.ai.tree;

import java.util.List;

public interface Node {

    boolean isLeaf();

    List<Node> getSuccessors();

    void addSuccessor(Node n);

    void setValue(int tokens);
}
