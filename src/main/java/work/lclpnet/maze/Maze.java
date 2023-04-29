package work.lclpnet.maze;

import work.lclpnet.maze.graph.Graph;
import work.lclpnet.maze.graph.Node;

import javax.annotation.Nullable;

public interface Maze<T extends Node> {

    Graph getGraph();

    @Nullable
    T getNode(int i);

    int getNodeId(T node);
}
