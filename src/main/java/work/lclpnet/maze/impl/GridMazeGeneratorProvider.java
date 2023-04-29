package work.lclpnet.maze.impl;

import work.lclpnet.maze.Maze;
import work.lclpnet.maze.MazeGenerator;
import work.lclpnet.maze.MazeGeneratorProvider;
import work.lclpnet.maze.graph.Graph;
import work.lclpnet.maze.graph.Graphs;
import work.lclpnet.maze.graph.Node;

import java.util.function.IntFunction;

public class GridMazeGeneratorProvider<T extends Node> implements MazeGeneratorProvider<T> {

    private final int width, height;
    private final IntFunction<T> nodeFactory;

    public GridMazeGeneratorProvider(int width, int height, IntFunction<T> nodeFactory) {
        this.width = width;
        this.height = height;
        this.nodeFactory = nodeFactory;
    }

    @Override
    public MazeGenerator<T> createGenerator() {
        Graph graph = Graphs.gridGraph(width, height);
        Maze<T> maze = new SimpleMaze<>(graph, nodeFactory);
        return new BasicMazeGenerator<>(maze, 0);
    }
}
