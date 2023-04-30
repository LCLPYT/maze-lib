package work.lclpnet.maze;

import work.lclpnet.maze.algorithm.MazeGenerationAlgorithm;
import work.lclpnet.maze.graph.Node;

import java.util.random.RandomGenerator;

/**
 * A utility class for creating and outputting mazes.
 * @param <T> The node type.
 */
public final class MazeCreator<T extends Node> {

    private final MazeGeneratorProvider<T> provider;
    private final MazeGenerationAlgorithm algorithm;
    private final MazeOutput output;

    public MazeCreator(MazeGeneratorProvider<T> provider, MazeGenerationAlgorithm algorithm, MazeOutput output) {
        this.provider = provider;
        this.algorithm = algorithm;
        this.output = output;
    }

    /**
     * Creates and writes a randomized maze.
     * @param random A random number generator.
     */
    public void create(RandomGenerator random) {
        MazeGenerator<T> generator = provider.createGenerator();
        Maze maze = generator.generateMaze(algorithm, random);
        output.writeMaze(maze);
    }
}
