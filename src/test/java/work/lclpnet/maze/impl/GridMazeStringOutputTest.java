package work.lclpnet.maze.impl;

import org.junit.jupiter.api.Test;
import work.lclpnet.maze.Maze;
import work.lclpnet.maze.graph.BasicNode;
import work.lclpnet.maze.graph.Graph;
import work.lclpnet.maze.graph.Graphs;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GridMazeStringOutputTest {

    @Test
    void writeMaze_all() {
        Graph graph = Graphs.gridGraph(5, 5);
        Maze maze = new SimpleMaze<>(graph, i -> new BasicNode());

        var output = new GridMazeStringOutput(5, 5);
        output.writeMaze(maze);

        assertEquals("""
###########
# # # # # #
###########
# # # # # #
###########
# # # # # #
###########
# # # # # #
###########
# # # # # #
###########
                """.trim(), output.getOutput().replaceAll("\\r\\n?", "\n"));
    }

    @Test
    void writeMaze_removed() {
        Graph graph = Graphs.gridGraph(5, 5);
        graph.removeEdge(0, 1);
        graph.removeEdge(1, 2);
        graph.removeEdge(2, 3);
        graph.removeEdge(1, 6);

        graph.removeEdge(14, 13);
        graph.removeEdge(14, 19);
        graph.removeEdge(24, 19);

        graph.removeEdge(17, 16);

        Maze maze = new SimpleMaze<>(graph, i -> new BasicNode());

        var output = new GridMazeStringOutput(5, 5);
        output.writeMaze(maze);

        assertEquals("""
###########
#       # #
### #######
# # # # # #
###########
# # # #   #
######### #
# #   # # #
######### #
# # # # # #
###########
                """.trim(), output.getOutput().replaceAll("\\r\\n?", "\n"));
    }
}