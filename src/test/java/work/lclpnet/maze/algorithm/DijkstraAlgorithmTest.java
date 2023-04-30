package work.lclpnet.maze.algorithm;

import org.junit.jupiter.api.Test;
import work.lclpnet.maze.graph.Graph;
import work.lclpnet.maze.graph.Graphs;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DijkstraAlgorithmTest {

    private Graph testGraph() {
        var graph = Graphs.gridGraph(3, 3);
        graph.removeEdge(3, 4);
        graph.removeEdge(3, 6);
        graph.removeEdge(7, 8);
        return graph;
    }

    @Test
    void distanceTo() {
        var graph = testGraph();
        var dijkstra = new DijkstraAlgorithm(graph, 0);
        assertEquals(0, dijkstra.distanceTo(0));
        assertEquals(1, dijkstra.distanceTo(1));
        assertEquals(1, dijkstra.distanceTo(3));
        assertEquals(2, dijkstra.distanceTo(2));
        assertEquals(2, dijkstra.distanceTo(4));
        assertEquals(3, dijkstra.distanceTo(5));
        assertEquals(3, dijkstra.distanceTo(7));
        assertEquals(4, dijkstra.distanceTo(6));
        assertEquals(4, dijkstra.distanceTo(8));
    }

    @Test
    void pathTo() {
        var graph = testGraph();
        var dijkstra = new DijkstraAlgorithm(graph, 0);
        assertArrayEquals(new int[] {0}, dijkstra.pathTo(0));
        assertArrayEquals(new int[] {0, 1}, dijkstra.pathTo(1));
        assertArrayEquals(new int[] {0, 3}, dijkstra.pathTo(3));
        assertArrayEquals(new int[] {0, 1, 2}, dijkstra.pathTo(2));
        assertArrayEquals(new int[] {0, 1, 4}, dijkstra.pathTo(4));
        assertArrayEquals(new int[] {0, 1, 4, 7}, dijkstra.pathTo(7));
        assertArrayEquals(new int[] {0, 1, 4, 7, 6}, dijkstra.pathTo(6));
    }
}