package work.lclpnet.maze.graph;

import java.util.Iterator;

public interface Node {

    Iterator<Node> getAdjacent();

    void connect(Node other);

    void disconnect(Node other);
}
