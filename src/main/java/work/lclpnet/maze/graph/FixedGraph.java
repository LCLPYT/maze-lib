package work.lclpnet.maze.graph;

public abstract class FixedGraph implements Graph {

    private final int nodeCount;

    public FixedGraph(int nodeCount) {
        if (nodeCount < 0) throw new IllegalArgumentException("Vertex count cannot be negative");

        this.nodeCount = nodeCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }
}
