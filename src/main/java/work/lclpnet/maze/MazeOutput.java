package work.lclpnet.maze;

import work.lclpnet.maze.graph.Node;

public interface MazeOutput<T extends Node> {

    void writeMaze(Maze<T> maze);
}
