package work.lclpnet.maze.algorithm;

import work.lclpnet.maze.graph.Graph;

import java.util.Stack;
import java.util.random.RandomGenerator;

public class RecursiveBacktrackingMazeGenerationAlgorithm implements MazeGenerationAlgorithm {

    protected RecursiveBacktrackingMazeGenerationAlgorithm() {}

    @Override
    public void apply(Graph graph, final int start, RandomGenerator random) {
        boolean[] visited = new boolean[graph.getNodeCount()];

        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        visited[start] = true;

        while (!stack.isEmpty()) {
            int node = stack.peek();

            int[] adj = graph.streamAdjacent(node).filter(i -> !visited[i]).toArray();
            if (adj.length == 0) {
                stack.pop();
                continue;
            }

            int randomIdx = random.nextInt(adj.length);
            int randomAdjNode = adj[randomIdx];

            graph.removeEdge(node, randomAdjNode);

            stack.push(randomAdjNode);
            visited[randomAdjNode] = true;
        }
    }

    public static MazeGenerationAlgorithm getInstance() {
        return InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final RecursiveBacktrackingMazeGenerationAlgorithm instance = new RecursiveBacktrackingMazeGenerationAlgorithm();
    }
}
