package work.lclpnet.maze.graph;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BasicNode implements Node {

    private final List<Node> adj = new ArrayList<>();

    @Override
    public Iterator<Node> getAdjacent() {
        return adj.iterator();
    }

    @Override
    public void connect(Node other) {
        adj.add(other);
    }

    @Override
    public void disconnect(Node other) {
        adj.remove(other);
    }
}
