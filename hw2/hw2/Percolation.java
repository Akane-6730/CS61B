package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int size;
    // Grid, true represents an open site, false represents a closed site
    private boolean[][] grid;
    // Used to check Percolation.
    private WeightedQuickUnionUF uf;
    // Union-Find to handle backwash
    private WeightedQuickUnionUF ufBackwash;
    // Cathe the number of open sites.
    private int openSitesCount;
    // Virtual top node
    private int virtualTop;
    // Virtual bottom node
    private int virtualBottom;


    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }
        size = N;
        grid = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufBackwash = new WeightedQuickUnionUF(N * N + 2);
        virtualTop = N * N;
        virtualBottom = N * N + 1;
        openSitesCount = 0;

        for (int i = 0; i < N; i++) {
            uf.union(getIndex(0, i), virtualTop);
            uf.union(getIndex(N - 1, i), virtualBottom);
            ufBackwash.union(getIndex(0, i), virtualTop);
        }
    }

    // Helper Method: Validates that row and col are within bounds
    private void validate(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IndexOutOfBoundsException("Row or column index out of bounds");
        }
    }

    // Helper Method: Validates that row and col are within bounds
    private boolean validateNeighbor(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        }
        return true;
    }

    // Helper Method: Converts 2D coordinates to 1D index
    private int getIndex(int row, int col) {
        return row * size + col;
    }

    // Helper Method: Connects the site to its open neighbors (up, down, left, right)
    private void connectedToNeighbors(int row, int col, int index) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            if (validateNeighbor(row + dx[i], col + dy[i]) && isOpen(row + dx[i], col + dy[i])) {
                uf.union(index, getIndex(row + dx[i], col + dy[i]));
                ufBackwash.union(index, getIndex(row + dx[i], col + dy[i]));
            }
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            openSitesCount++;
            connectedToNeighbors(row, col, getIndex(row, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) {
            return ufBackwash.connected(virtualTop, getIndex(row, col));
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(virtualTop, virtualBottom);
    }
}
