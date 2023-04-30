package work.lclpnet.maze.impl;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import work.lclpnet.maze.Maze;
import work.lclpnet.maze.graph.Graph;
import work.lclpnet.maze.graph.Graphs;
import work.lclpnet.maze.graph.Node;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public class SimpleMaze<T extends Node> implements Maze {

    private final Int2ObjectMap<T> nodes;
    private final Object2IntMap<T> ids;
    private final Graph graph;

    public SimpleMaze(Graph graph, IntFunction<T> nodeFactory) {
        this.graph = graph;

        final int size = graph.getNodeCount();

        // build nodes
        int[] index = IntStream.range(0, size).toArray();
        Object[] nodes = graph.streamNodes().mapToObj(nodeFactory).toArray();

        this.nodes = Int2ObjectMaps.unmodifiable(new Int2ObjectArrayMap<>(index, nodes));

        // build ids
        var ids = new Object2IntArrayMap<T>(size);

        for (var entry : this.nodes.int2ObjectEntrySet()) {
            ids.put(entry.getValue(), entry.getIntKey());
        }

        this.ids = Object2IntMaps.unmodifiable(ids);

        // connect nodes
        for (Node node : this.nodes.values()) {
            int i = getNodeId(node);

            for (int j : graph.getAdjacent(i)) {
                Node other = getNode(j);
                if (other == null) continue;

                node.connect(other);
                other.connect(node);
            }
        }
    }

    public SimpleMaze(Collection<T> values, IntFunction<Graph> graphFactory) {
        final int size = values.size();

        // build nodes
        int[] index = IntStream.range(0, size).toArray();
        Object[] nodes = values.toArray();

        this.nodes = Int2ObjectMaps.unmodifiable(new Int2ObjectArrayMap<>(index, nodes));

        // build ids
        var ids = new Object2IntArrayMap<T>(size);
        ids.defaultReturnValue(-1);

        for (var entry : this.nodes.int2ObjectEntrySet()) {
            ids.put(entry.getValue(), entry.getIntKey());
        }

        this.ids = Object2IntMaps.unmodifiable(ids);

        // build graph
        this.graph = graphFactory.apply(size);

        for (T node : values) {
            final int i = ids.getInt(node);

            for (Node other : node.getAdjacent()) {
                int j = ids.getInt(other);
                this.graph.addEdge(i, j);
            }
        }
    }

    @Nullable
    @Override
    public Node getNode(int i) {
        return nodes.get(i);
    }

    @Override
    public int getNodeId(Node node) {
        return ids.getInt(node);
    }

    @Override
    public Graph getGraph() {
        return graph;
    }

    public Graph createPassageGraph() {
        Graph inverted = Graphs.undirected(graph.getNodeCount());

        for (int i : graph.iterateNodes()) {
            Node node = getNode(i);
            if (node == null) continue;

            for (Node adj : node.getAdjacent()) {
                int j = getNodeId(adj);

                if (!graph.hasEdge(i, j)) {
                    inverted.addEdge(i, j);
                }
            }
        }

        return inverted;
    }
}
