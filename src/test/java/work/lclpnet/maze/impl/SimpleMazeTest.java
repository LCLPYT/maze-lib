package work.lclpnet.maze.impl;

import org.junit.jupiter.api.Test;
import work.lclpnet.maze.graph.BasicBiNode;
import work.lclpnet.maze.graph.BasicNode;
import work.lclpnet.maze.graph.Graphs;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SimpleMazeTest {

    @Test
    void newMazeFromNodes() {
        BasicBiNode a = new BasicBiNode();
        BasicBiNode b = new BasicBiNode();
        BasicBiNode c = new BasicBiNode();
        BasicBiNode d = new BasicBiNode();

        a.connect(b);
        b.connect(c);
        b.connect(d);

        var nodes = Set.of(a, b, c, d);
        var maze = new SimpleMaze<>(nodes, Graphs::undirected);

        var graph = maze.getGraph();

        nodes.stream().parallel().forEach(node -> {
            int i = maze.getNodeId(node);

            Set<Integer> expected = node.streamAdjacent().map(maze::getNodeId).collect(Collectors.toSet());
            Set<Integer> actual = graph.streamAdjacent(i).boxed().collect(Collectors.toSet());

            assertEquals(expected, actual);
        });
    }

    @Test
    void newMazeFromGraph() {
        var graph = Graphs.gridGraph(3, 3);
        var maze = new SimpleMaze<>(graph, i -> new BasicNode());

        graph.streamNodes().parallel().mapToObj(maze::getNode).forEach(node -> {
            assertNotNull(node);
            int i = maze.getNodeId(node);

            Set<Integer> expected = graph.streamAdjacent(i).boxed().collect(Collectors.toSet());
            Set<Integer> actual = node.streamAdjacent().map(maze::getNodeId).collect(Collectors.toSet());

            assertEquals(expected, actual);
        });
    }

    @Test
    void createPassageGraph() {
        var graph = Graphs.gridGraph(3, 3);
        var maze = new SimpleMaze<>(graph, i -> new BasicNode());

        graph.removeEdge(0, 1);
        graph.removeEdge(1, 4);
        graph.removeEdge(4, 7);
        graph.removeEdge(7, 8);
        graph.removeEdge(6, 7);

        var output = new GridMazeStringOutput(3, 3);
        output.writeMaze(maze);

        assertEquals("""
#######
#   # #
### ###
# # # #
### ###
#     #
#######
                """.trim(), output.getOutput().replaceAll("\\r\\n?", "\n"));

        var passageGraph = maze.createPassageGraph();

        assertEquals(1, passageGraph.streamAdjacent(0).count());
        assertEquals(2, passageGraph.streamAdjacent(1).count());
        assertEquals(0, passageGraph.streamAdjacent(2).count());
        assertEquals(0, passageGraph.streamAdjacent(3).count());
        assertEquals(2, passageGraph.streamAdjacent(4).count());
        assertEquals(0, passageGraph.streamAdjacent(5).count());
        assertEquals(1, passageGraph.streamAdjacent(6).count());
        assertEquals(3, passageGraph.streamAdjacent(7).count());
        assertEquals(1, passageGraph.streamAdjacent(8).count());
    }
}