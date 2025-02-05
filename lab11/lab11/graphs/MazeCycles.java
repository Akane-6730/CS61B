package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int[] parent;
    boolean cycleFound = false;

    public MazeCycles(Maze m) {
        super(m);
        parent = new int[m.N() * m.N()];
    }

    @Override
    public void solve() {
        dfs(0, -1);
    }

    // Helper methods go here
    private void dfs(int s, int p) {
        if (cycleFound) {
            return;
        }

        marked[s] = true;
        announce();
        for (int v : maze.adj(s)) {
            if (cycleFound) {
                return;
            }
            if (!marked[v]) {
                parent[v] = s;
                dfs(v, s);
            } else if (v != p) {
                cycleFound = true;
                edgeTo[v] = s;
                for (int temp = s; temp != v; temp = parent[temp]) {
                    edgeTo[temp] = parent[temp];
                }
                announce();
                return;
            }
        }
    }
}

