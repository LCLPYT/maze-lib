package work.lclpnet.maze.graph;


import java.util.ArrayList;
import java.util.List;

/**
 * A basic node backed by an {@link ArrayList}.
 * Adding / removing adjacent nodes takes O(n) time.
 */
public class BasicNode implements Node {

    private final List<Node> adj = new ArrayList<>();

    @Override
    public Iterable<Node> getAdjacent() {
        return adj;
    }

    @Override
    public void connect(Node other) {
        if (!adj.contains(other)) {
            adj.add(other);
        }
    }

    @Override
    public void disconnect(Node other) {
        adj.remove(other);
    }
}
