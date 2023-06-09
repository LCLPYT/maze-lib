package work.lclpnet.maze.impl;

import work.lclpnet.maze.Maze;
import work.lclpnet.maze.MazeGenerator;
import work.lclpnet.maze.algorithm.MazeGenerationAlgorithm;
import work.lclpnet.maze.graph.Node;

import java.util.random.RandomGenerator;

public class BasicMazeGenerator<T extends Node> implements MazeGenerator<T> {

    private final Maze maze;
    private final int start;

    public BasicMazeGenerator(Maze maze, int start) {
        this.maze = maze;
        this.start = start;
    }

    @Override
    public Maze generateMaze(MazeGenerationAlgorithm algorithm, RandomGenerator random) {
        algorithm.apply(maze.getGraph(), start, random);

        return maze;
    }
}
