package work.lclpnet.maze.graph;

import it.unimi.dsi.fastutil.ints.IntIterable;
import it.unimi.dsi.fastutil.ints.IntIterator;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * An undirected graph with a fixed vertex count.
 * Edges are stored in an adjacency matrix, which grows with O(vertexCount^2) but can be accessed in O(1).
 * This implementation is suited for graphs with smaller vertex counts.
 * For bigger graphs, use {@link FixedArrayUndirectedGraph}.
 */
public class FixedMatrixUndirectedGraph extends FixedGraph {

    private final boolean[][] adj;

    public FixedMatrixUndirectedGraph(int vertexCount) {
        super(vertexCount);

        this.adj = new boolean[vertexCount][vertexCount];

        for (int i = 0; i < vertexCount; i++) {
            Arrays.fill(adj[i], false);
        }
    }

    @Override
    public IntIterable getAdjacent(final int node) {
        return () -> new IntIterator() {
            private int cursor = 0;
            private int next = -1;

            @Override
            public boolean hasNext() {
                // find next adjacent node
                while (next == -1 && cursor < getNodeCount()) {
                    if (hasEdge(node, cursor)) {
                        next = cursor;
                    }
                    cursor++;
                }

                return next != -1;
            }

            @Override
            public int nextInt() {
                if (next == -1) throw new IllegalStateException("Next element not calculated");
                int ret = next;
                next = -1;
                return ret;
            }
        };
    }

    @Override
    public boolean hasEdge(int x, int y) {
        return adj[x][y];  // it does not matter which combination to check as both are the same
    }

    @Override
    public void addEdge(int x, int y) {
        if (x == y) throw new IllegalArgumentException("Reflexive nodes are not allowed");
        adj[x][y] = true;
        adj[y][x] = true;
    }

    @Override
    public void removeEdge(int x, int y) {
        adj[x][y] = false;
        adj[y][x] = false;
    }

    @Override
    public IntStream streamNodes() {
        return IntStream.range(0, getNodeCount());
    }
}
