package work.lclpnet.maze.graph;


import java.util.ArrayList;
import java.util.List;

/**
 * The same as {@link BasicNode}, but connected nodes are automatically connected back (useful for undirected graphs).
 */
public class BasicBiNode implements Node {

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

        if (other instanceof BasicBiNode otherBiNode) {
            otherBiNode.adj.add(this);
        }
    }

    @Override
    public void disconnect(Node other) {
        adj.remove(other);

        if (other instanceof BasicBiNode otherBiNode) {
            otherBiNode.adj.remove(this);
        }
    }
}
