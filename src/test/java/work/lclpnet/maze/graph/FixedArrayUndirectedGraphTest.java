package work.lclpnet.maze.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FixedArrayUndirectedGraphTest {

    private final GraphImplementationTest test = new GraphImplementationTest(FixedArrayUndirectedGraph::new);

    @Test
    void getAdjacent_count_correct() {
        test.getAdjacent_count_correct();
    }

    @Test
    void hasEdge_outOfBounds_throws() {
        test.hasEdge_outOfBounds_throws();
    }

    @Test
    void addEdge_reflexive_throws() {
        test.addEdge_reflexive_throws();
    }

    @Test
    void addEdge_valid_hasEdge() {
        test.addEdge_valid_hasEdge();
    }

    @Test
    void addEdge_outOfBounds_throws() {
        test.addEdge_outOfBounds_throws();
    }

    @Test
    void removeEdge_valid_removed() {
        test.removeEdge_valid_removed();
    }

    @Test
    void removeEdge_outOfBounds_throws() {
        test.removeEdge_outOfBounds_throws();
    }

    @Test
    void addEdge_full_doubled() {
        var graph = new FixedArrayUndirectedGraph(10, 2);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);

        assertEquals(2, graph.edges[0]);
        assertEquals(2, graph.adj[0].length);

        graph.addEdge(0, 3);
        assertEquals(3, graph.edges[0]);
        assertEquals(4, graph.adj[0].length);

        graph.addEdge(0, 4);
        assertEquals(4, graph.adj[0].length);

        graph.addEdge(0, 5);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5, 0, 0, 0}, graph.adj[0]);
    }

    @Test
    void removeEdge_half_halved() {
        var graph = new FixedArrayUndirectedGraph(10, 8);
        assertEquals(0, graph.edges[0]);
        assertEquals(8, graph.adj[0].length);

        // nothing should happen, as the edge does not exist
        graph.removeEdge(0, 1);
        assertEquals(0, graph.edges[0]);
        assertEquals(8, graph.adj[0].length);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);

        graph.removeEdge(0, 1);
        assertEquals(4, graph.adj[0].length);
        assertEquals(4, graph.adj[1].length);

        graph.removeEdge(0, 2);
        assertEquals(2, graph.adj[0].length);
        assertEquals(4, graph.adj[2].length);

        graph.removeEdge(0, 3);
        assertEquals(2, graph.adj[0].length);
        assertEquals(4, graph.adj[3].length);

        assertArrayEquals(new int[] {0, 0}, graph.adj[0]);
        assertArrayEquals(new int[] {0, 0, 0, 0}, graph.adj[1]);
        assertArrayEquals(new int[] {0, 0, 0, 0}, graph.adj[2]);
        assertArrayEquals(new int[] {0, 0, 0, 0}, graph.adj[3]);
    }
}