package work.lclpnet.maze.impl;

import work.lclpnet.maze.Maze;
import work.lclpnet.maze.MazeOutput;
import work.lclpnet.maze.graph.Graph;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

public class GridMazeStringOutput implements MazeOutput {

    private final int width, height;
    private final char wall, empty;
    private String output;

    public GridMazeStringOutput(int width, int height) {
        this(width, height, '#', ' ');
    }

    public GridMazeStringOutput(int width, int height, char wall, char empty) {
        this.width = width;
        this.height = height;
        this.wall = wall;
        this.empty = empty;
    }

    @Override
    public void writeMaze(Maze maze) {
        // prepare board
        char[][] board = getStringBoard();
        Graph graph = maze.getGraph();

        // carve walls
        BiFunction<Integer, Integer, Integer> node = (x, y) -> width * y + x;
        UnaryOperator<Integer> ix = (i) -> i % width;
        UnaryOperator<Integer> iy = (i) -> i / height;
        BiConsumer<Integer, Integer> removeIf = (a, b) -> {
            if (!graph.hasEdge(a, b)) {
                int x = ix.apply(a);
                int y = iy.apply(a);

                int dx = ix.apply(b) - x;
                int dy = iy.apply(b) - y;

                board[2 * y + 1 + dy][2 * x + 1 + dx] = empty;
            }
        };

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x < height - 1) {  // right
                    removeIf.accept(node.apply(x, y), node.apply(x + 1, y));
                }
                if (x > 0) {  // left
                    removeIf.accept(node.apply(x, y), node.apply(x - 1, y));
                }
                if (y > 0) {  // up
                    removeIf.accept(node.apply(x, y), node.apply(x, y - 1));
                }
                if (y < height - 1) {  // down
                    removeIf.accept(node.apply(x, y), node.apply(x, y + 1));
                }
            }
        }

        // to string
        StringBuilder builder = new StringBuilder();

        boolean first = true;

        for (char[] row : board) {
            if (first) first = false;
            else builder.append(System.lineSeparator());

            builder.append(new String(row));
        }

        output = builder.toString();
    }

    private char[][] getStringBoard() {
        final int h = 2 * height + 1;
        final int w = 2 * width + 1;

        char[][] board = new char[h][w];

        for (int y = 0; y < h; y++) {
            if (y % 2 == 0) {
                Arrays.fill(board[y], wall);
                continue;
            }

            for (int x = 0; x < w; x++) {
                if (x % 2 == 0) {
                    board[y][x] = wall;
                } else {
                    board[y][x] = empty;
                }
            }
        }

        return board;
    }

    public String getOutput() {
        return output;
    }
}
