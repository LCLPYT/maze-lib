package work.lclpnet.maze.algorithm;

import work.lclpnet.maze.datastructure.FibonacciHeap;
import work.lclpnet.maze.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DijkstraAlgorithm {

    private final int[] dist;
    private final int[] prev;

    public DijkstraAlgorithm(Graph graph, int start) {
        dist = new int[graph.getNodeCount()];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        prev = new int[graph.getNodeCount()];
        Arrays.fill(prev, -1);

        // build priority queue
        FibonacciHeap<Integer> queue = new FibonacciHeap<>();

        @SuppressWarnings("unchecked")
        FibonacciHeap.Entry<Integer>[] entries = new FibonacciHeap.Entry[graph.getNodeCount()];

        for (int i : graph.iterateNodes()) {
            entries[i] = queue.enqueue(i, dist[i]);
        }

        while (queue.size() != 0) {
            int i = queue.dequeueMin().getValue();

            for (int j : graph.getAdjacent(i)) {
                int newDist = dist[i] + 1;

                if (newDist < dist[j]) {
                    dist[j] = newDist;
                    prev[j] = i;

                    queue.decreaseKey(entries[j], newDist);
                }
            }
        }
    }

    public int distanceTo(int i) {
        return dist[i];
    }

    public int[] pathTo(int i) {
        List<Integer> path = new ArrayList<>();

        int next = i;
        while (next != -1) {
            path.add(next);
            next = prev[next];
        }

        final int size = path.size();

        int[] ret = new int[size];
        int j = size - 1;

        for (int k : path) {
            ret[j--] = k;
        }

        return ret;
    }
}
