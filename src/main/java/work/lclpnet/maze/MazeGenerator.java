package work.lclpnet.maze;

import work.lclpnet.maze.algorithm.MazeGenerationAlgorithm;
import work.lclpnet.maze.graph.Node;

import java.util.random.RandomGenerator;

public interface MazeGenerator<T extends Node> {

    Maze<T> generateMaze(MazeGenerationAlgorithm algorithm, RandomGenerator random);
}
