package work.lclpnet.maze.graph;

import it.unimi.dsi.fastutil.ints.IntIterable;
import it.unimi.dsi.fastutil.ints.IntIterator;

import java.util.Arrays;

/**
 * An undirected graph with a fixed vertex count.
 * Edges are stored in an adjacency list, which grows at most O(abs(vertexCount) + abs(edgeCount)) but can be accessed in O(n).
 * This implementation is better suited for graphs with bigger vertex counts.
 * For smaller graphs, you can use {@link FixedMatrixUndirectedGraph} if you need the extra search performance.
 */
public class FixedArrayUndirectedGraph extends FixedGraph {

    final int[] edges;
    final int[][] adj;

    public FixedArrayUndirectedGraph(int nodeCount) {
        this(nodeCount, 4);  // assume grid graph, where most nodes have 4 edges
    }

    public FixedArrayUndirectedGraph(int nodeCount, int expectedEdgeCount) {
        super(nodeCount);

        this.adj = new int[nodeCount][expectedEdgeCount];
        this.edges = new int[nodeCount];

        Arrays.fill(edges, 0);
    }

    @Override
    public IntIterable getAdjacent(final int node) {
        return () -> new IntIterator() {
            private int cursor = 0;
            private int next = -1;

            @Override
            public boolean hasNext() {
                // find next adjacent node
                if (cursor < edges[node]) {
                    next = cursor++;
                    return true;
                }

                return false;
            }

            @Override
            public int nextInt() {
                if (next == -1) throw new IllegalStateException("Next element not calculated");
                int idx = next;
                next = -1;
                return adj[node][idx];
            }
        };
    }

    @Override
    public boolean hasEdge(int x, int y) {
        // it does not matter which combination to check as both are the same
        // could still be improved to O(log(edges[x])) with binary search
        synchronized (this) {
            for (int i = 0; i < edges[x]; i++) {
                if (adj[x][i] == y) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void addEdge(int x, int y) {
        if (x == y) throw new IllegalArgumentException("Reflexive nodes are not allowed");

        trackEdge(x, y);
        trackEdge(y, x);
    }

    private void trackEdge(int a, int b) {
        if (hasEdge(a, b)) return;

        synchronized (this) {
            if (edges[a] >= adj[a].length) {
                // adj[a] is too small. Double it in size
                int[] bigger = new int[adj[a].length * 2];
                System.arraycopy(adj[a], 0, bigger, 0, adj[a].length);
                adj[a] = bigger;
            }

            adj[a][edges[a]++] = b;
        }
    }

    @Override
    public void removeEdge(int x, int y) {
        if (x == y) return;  // reflexive edges are not allowed and therefore must not be removed

        untrackEdge(x, y);
        untrackEdge(y, x);
    }

    private void untrackEdge(int a, int b) {
        synchronized (this) {
            int idx = -1;

            for (int i = 0; i < edges[a]; i++) {
                if (adj[a][i] == b) {
                    idx = i;
                    break;
                }
            }

            if (idx == -1) return;

            final int halfLength = adj[a].length / 2;
            int lesserLength = Math.max(2, edges[a] <= halfLength ? halfLength : adj[a].length);

            int[] lesser = new int[lesserLength];
            System.arraycopy(adj[a], 0, lesser, 0, idx);
            System.arraycopy(adj[a], idx + 1, lesser, idx, edges[a] - idx - 1);

            adj[a] = lesser;
            edges[a]--;
        }
    }
}
