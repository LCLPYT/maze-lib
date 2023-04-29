package work.lclpnet.maze.algorithm;

import work.lclpnet.maze.graph.Graph;

import java.util.random.RandomGenerator;

public interface MazeGenerationAlgorithm {

    void apply(Graph graph, int start, RandomGenerator random);
}
