package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.LinkedList;

public class Solver {

    private MinPQ<SearchNode> pq = new MinPQ<>();
    private SearchNode goal;


    /** Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        SearchNode init = new SearchNode(initial, 0, null);
        pq.insert(init);

        while (!pq.isEmpty()) {
            SearchNode oldNode = pq.delMin();
            if (oldNode.state.isGoal()) {
                goal = oldNode;
                return;
            }

            for (WorldState neighbor : oldNode.state.neighbors()) {
                // A node can't be equal to its parent, so only the grandparent needs to be checked.
                if (oldNode.prev == null || !neighbor.equals(oldNode.prev.state)) {
                    pq.insert(new SearchNode(neighbor, oldNode.moves + 1, oldNode));
                }
            }
        }
    }

    /**
     *  Returns the minimum number of moves to solve the puzzle starting
     *  at the initial WorldState.
     */
    public int moves() {
        return goal.moves;
    }

    public Iterable<WorldState> solution() {
        LinkedList<WorldState> solution = new LinkedList<>();

        if (goal == null) {
            return solution;
        }

        SearchNode node = goal;
        while (node != null) {
            solution.addFirst(node.state);
            node = node.prev;
        }

        return solution;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final WorldState state;
        private final int moves;
        private final SearchNode prev;
        private final int priority;

        private SearchNode(WorldState s, int m, SearchNode p) {
            state = s;
            moves = m;
            prev = p;
            priority = moves + state.estimatedDistanceToGoal();
        }

        @Override
        public int compareTo(SearchNode o) {
            return this.priority - o.priority;
        }
    }
}
