package work.lclpnet.maze;

import work.lclpnet.maze.graph.Graph;
import work.lclpnet.maze.graph.Node;

import javax.annotation.Nullable;

public interface Maze {

    Graph getGraph();

    @Nullable
    Node getNode(int i);

    int getNodeId(Node node);
}
