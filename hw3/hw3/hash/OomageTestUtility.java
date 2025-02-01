package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    /** A utility function that returns true if the given oomages
     * have hashCodes that would distribute them fairly evenly across
     * M buckets. To do this, convert each oomage's hashcode in the
     * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
     * and ensure that no bucket has fewer than N / 50 Oomages
     * and no bucket has more than N / 2.5 Oomages.
     */
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        // Array to store the count of Oomages in each of the M buckets
        int[] buckets = new int[M];
        int N = oomages.size();

        // Record the number of oomages of every bucket.
        for (Oomage oomage : oomages) {
            int bucketNum = (oomage.hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketNum]++;
        }

        double lowerbound = N / 50.0;
        double upperbound = N / 2.5;

        for (int count : buckets) {
            if (count < lowerbound || count > upperbound) {
                return false;
            }
        }
        return true;
    }
}
