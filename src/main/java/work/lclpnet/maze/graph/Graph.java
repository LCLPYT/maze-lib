package work.lclpnet.maze.graph;

import it.unimi.dsi.fastutil.ints.IntIterable;
import it.unimi.dsi.fastutil.ints.IntIterators;

import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public interface Graph {

    int getNodeCount();

    IntIterable getAdjacent(int node);

    boolean hasEdge(int x, int y);

    void addEdge(int x, int y);

    void removeEdge(int x, int y);

    default IntIterable iterateNodes() {
        return () -> IntIterators.fromTo(0, getNodeCount());
    }

    default IntStream streamNodes() {
        return IntStream.range(0, getNodeCount());
    }

    default IntStream streamAdjacent(int node) {
        return StreamSupport.intStream(getAdjacent(node).spliterator(), false);
    }
}
