package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;


public class PercolationStats {
    private Percolation item;
    private int T;
    private double[] thresholds;
    private static final double K = 1.96;

    private double sumOfThreshold;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.T = T;
        thresholds = new double[T];
        double thrshold = 0;

        for (int i = 0; i < T; i++) {
            item = pf.make(N);
            while (!item.percolates()) {
                item.open(StdRandom.uniform(N), StdRandom.uniform(N));
            }
            thresholds[i] = 1.0 * item.numberOfOpenSites() / (N * N);
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLow() {
        return mean() - K * Math.sqrt(stddev() / T);
    }

    public double confidenceHigh() {
        return mean() + K * Math.sqrt(stddev() / T);
    }
}
