package work.lclpnet.maze.graph;

import java.util.function.IntFunction;

import static org.junit.jupiter.api.Assertions.*;

public class GraphImplementationTest {

    private final IntFunction<Graph> graphFactory;
    
    GraphImplementationTest(IntFunction<Graph> graphFactory) {
        this.graphFactory = graphFactory;
    }
    
    void getAdjacent_count_correct() {
        var graph = graphFactory.apply(5);
        assertEquals(0, graph.streamAdjacent(0).count());

        graph.addEdge(0, 1);
        assertEquals(1, graph.streamAdjacent(0).count());

        graph.addEdge(0, 1);
        assertEquals(1, graph.streamAdjacent(0).count());

        graph.addEdge(0, 4);
        assertEquals(2, graph.streamAdjacent(0).count());
    }

    void hasEdge_outOfBounds_throws() {
        assertThrows(IndexOutOfBoundsException.class, () -> graphFactory.apply(5).hasEdge(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> graphFactory.apply(5).hasEdge(5, 5));
    }

    void addEdge_reflexive_throws() {
        assertThrows(IllegalArgumentException.class, () -> graphFactory.apply(5).addEdge(1, 1));
        assertThrows(IllegalArgumentException.class, () -> graphFactory.apply(5).addEdge(0, 0));
    }

    void addEdge_valid_hasEdge() {
        var graph = graphFactory.apply(5);
        assertFalse(graph.hasEdge(0, 1));
        graph.addEdge(0, 1);
        assertTrue(graph.hasEdge(0, 1));
    }

    void addEdge_outOfBounds_throws() {
        assertThrows(IndexOutOfBoundsException.class, () -> graphFactory.apply(5).addEdge(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> graphFactory.apply(5).addEdge(0, 5));
    }

    void removeEdge_valid_removed() {
        var graph = graphFactory.apply(5);
        graph.addEdge(0, 1);
        assertTrue(graph.hasEdge(0, 1));
        graph.removeEdge(0, 1);
        assertFalse(graph.hasEdge(0, 1));
    }

    void removeEdge_outOfBounds_throws() {
        assertThrows(IndexOutOfBoundsException.class, () -> graphFactory.apply(5).removeEdge(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> graphFactory.apply(5).removeEdge(0, 5));
    }
}
