package work.lclpnet.maze.algorithm;

import org.junit.jupiter.api.Test;
import work.lclpnet.maze.MazeCreator;
import work.lclpnet.maze.graph.BasicNode;
import work.lclpnet.maze.graph.Graph;
import work.lclpnet.maze.graph.Graphs;
import work.lclpnet.maze.impl.GridMazeGeneratorProvider;
import work.lclpnet.maze.impl.GridMazeStringOutput;

import java.util.Random;
import java.util.function.IntPredicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecursiveBacktrackingMazeGenerationAlgorithmTest {

    private static final int GRID_WIDTH = 5;
    private static final int GRID_HEIGHT = 5;

    @Test
    void generateMaze_acyclic() {
        Graph graph = Graphs.gridGraph(GRID_WIDTH, GRID_HEIGHT);

        IntPredicate hasCycle = i -> new CycleChecker(graph).inCycle(i);

        // every node must be in some kind of cycle
        assertTrue(graph.streamNodes().parallel().allMatch(hasCycle));

        MazeGenerationAlgorithm algorithm = RecursiveBacktrackingMazeGenerationAlgorithm.getInstance();
        algorithm.apply(graph, 0, new Random());

        // no node should be in a cycle, because every node was connected from the beginning
        assertTrue(graph.streamNodes().parallel().noneMatch(hasCycle));
    }

    @Test
    void deterministic() {
        var provider = new GridMazeGeneratorProvider<>(GRID_WIDTH, GRID_HEIGHT, i -> new BasicNode());
        var algorithm = RecursiveBacktrackingMazeGenerationAlgorithm.getInstance();
        var output = new GridMazeStringOutput(GRID_WIDTH, GRID_HEIGHT);

        Random random = new Random(123);
        new MazeCreator<>(provider, algorithm, output).create(random);

        assertEquals("""
###########
# #       #
# ####### #
#   #   # #
### # # # #
# # # #   #
# # # #####
#   #     #
# ####### #
#         #
###########
                """.trim(), output.getOutput().replaceAll("\\r\\n?", "\n"));
    }

    private static class CycleChecker {

        private final Graph graph;
        private final boolean[] dfsVisited;

        private CycleChecker(Graph graph) {
            this.graph = graph;
            this.dfsVisited = new boolean[graph.getNodeCount()];
        }

        public boolean inCycle(int node) {
            return inCycle(node, -1);
        }

        private boolean inCycle(int node, int parent) {
            dfsVisited[node] = true;

            var iterator = graph.getAdjacent(node).iterator();

            while (iterator.hasNext()) {
                int n = iterator.nextInt();

                if (!dfsVisited[n]) {
                    if (inCycle(n, node)) {
                        return true;
                    }

                    continue;
                }

                if (n != parent) {
                    return true;
                }
            }

            return false;
        }
    }
}