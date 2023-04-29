package work.lclpnet.maze;

import work.lclpnet.maze.graph.Node;

public interface MazeGeneratorProvider<T extends Node> {

    MazeGenerator<T> createGenerator();
}
