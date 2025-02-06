package lab11.graphs;

import java.util.PriorityQueue;

/**
 * @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        // Initialize all distances in distTo array to Integer.MAX_VALUE
        for (int i = 0; i < distTo.length; i++) {
            distTo[i] = Integer.MAX_VALUE;
        }

        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /**
     * Estimate of the distance from v to the target.
     */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /**
     * Finds vertex estimated to be closest to target.
     */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    private static class Node implements Comparable<Node> {
        int vertex;
        int priority;

        Node(int v, int p) {
            this.vertex = v;
            this.priority = p;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.priority, other.priority);
        }
    }


    /**
     * Performs an A star search from vertex s.
     */
    private void astar(int s) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Node source = new Node(s, 0);
        distTo[s] = 0;
        pq.add(source);

        while (!pq.isEmpty()) {
            int v = pq.poll().vertex;
            if (marked[v]) {
                continue;
            }

            marked[v] = true;
            announce();

            if (v == t) {
                targetFound = true;
                return;
            }

            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    if (distTo[v] + 1 < distTo[w]) {
                        distTo[w] = distTo[v] + 1;
                        edgeTo[w] = v;
                        pq.add(new Node(w, distTo[w] + h(w)));
                    }
                }
            }
        }

    }

    @Override
    public void solve() {
        astar(s);
    }
}
