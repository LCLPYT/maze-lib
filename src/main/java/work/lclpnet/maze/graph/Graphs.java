package work.lclpnet.maze.graph;

import java.util.function.BiFunction;
import java.util.function.IntFunction;

public class Graphs {

    private Graphs() {}

    /**
     * Creates a new undirected graph.
     * Depending on the vertexCount, a suiting graph implementation will be chosen.
     * @param vertexCount The vertex count.
     * @return An undirected graph with the given vertex count.
     */
    public static Graph undirected(int vertexCount) {
        if (vertexCount <= 32) {
            // sizeof(boolean) * vertexCount^2 = 8 byte * 32^2 = 8 kb + overhead (approx.)
            return new FixedMatrixUndirectedGraph(vertexCount);
        } else {
            // for bigger vertex counts, use adjacency list => more memory efficient
            return new FixedArrayUndirectedGraph(vertexCount);
        }
    }

    public static Graph gridGraph(int width, int height) {
        return gridGraph(width, height, Graphs::undirected);
    }

    public static Graph gridGraph(int width, int height, IntFunction<Graph> graphFactory) {
        Graph graph = graphFactory.apply(width * height);

        BiFunction<Integer, Integer, Integer> node = (x, y) -> width * y + x;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width - 1; x++) {
                graph.addEdge(node.apply(x, y), node.apply(x + 1, y));
            }
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height - 1; y++) {
                graph.addEdge(node.apply(x, y), node.apply(x, y + 1));
            }
        }

        return graph;
    }
}
