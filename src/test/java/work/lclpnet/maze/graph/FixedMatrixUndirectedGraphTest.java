package work.lclpnet.maze.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FixedMatrixUndirectedGraphTest {

    private final GraphImplementationTest test = new GraphImplementationTest(FixedMatrixUndirectedGraph::new);

    @Test
    void init_negativeCount_throws() {
        assertThrows(IllegalArgumentException.class, () -> new FixedMatrixUndirectedGraph(-1));
        assertThrows(IllegalArgumentException.class, () -> new FixedMatrixUndirectedGraph(-100));
    }

    @Test
    void getNodeCount_given_echo() {
        assertEquals(0, new FixedMatrixUndirectedGraph(0).getNodeCount());
        assertEquals(1, new FixedMatrixUndirectedGraph(1).getNodeCount());
        assertEquals(5, new FixedMatrixUndirectedGraph(5).getNodeCount());
    }

    @Test
    void getAdjacent_count_correct() {
        test.getAdjacent_count_correct();
    }

    @Test
    void hasEdge_outOfBounds_throws() {
        test.hasEdge_outOfBounds_throws();
        assertThrows(IndexOutOfBoundsException.class, () -> new FixedMatrixUndirectedGraph(5).hasEdge(0, 5));
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
}