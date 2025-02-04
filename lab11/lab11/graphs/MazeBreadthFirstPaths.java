package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> q = new ArrayDeque<>();
        q.add(s);
        marked[s] = true;
        announce();

        if (s == t) {
            targetFound = true;
        }

        if (targetFound) {
            return;
        }

        while (!q.isEmpty()) {
            int oldHead = q.remove();
            for (int v : maze.adj(oldHead)) {
                if (!marked[v]) {
                    marked[v] = true;
                    distTo[v] = distTo[oldHead] + 1;
                    edgeTo[v] = oldHead;
                    announce();
                    q.add(v);
                    if (v == t) {
                        return;
                    }
                }
            }
        }

    }


    @Override
    public void solve() {
        bfs();
    }
}

