package work.lclpnet.maze.graph;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Node {

    Iterable<Node> getAdjacent();

    void connect(Node other);

    void disconnect(Node other);

    default Stream<Node> streamAdjacent() {
        return StreamSupport.stream(getAdjacent().spliterator(), false);
    }
}
