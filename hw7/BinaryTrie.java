import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


public class BinaryTrie implements Serializable {
    // alphabet size of extended ASCII
    private static final int R = 256;

    private Node root;

    // Huffman trie node
    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        // initialize priority queue with singleton trees
        MinPQ<Node> pq = new MinPQ<Node>();
        for (char c = 0; c < R; c += 1) {
            if (frequencyTable.containsKey(c)) {
                pq.insert(new Node(c, frequencyTable.get(c), null, null));
            }
        }

        // merge two smallest trees
        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }

        root = pq.delMin();
    }

    /* finds the longest prefix that matches the given querySequence
     * and returns a Match object for that Match */

    public Match longestPrefixMatch(BitSequence querySequence) {
        Node current = root;
        int i = 0;
        BitSequence sequence = new BitSequence();
        while (current != null && !current.isLeaf()) {
            int b = querySequence.bitAt(i);
            sequence = sequence.appended(b);
            if (b == 0) {
                current = current.left;
            } else if (b == 1) {
                current = current.right;
            }
            i += 1;
        }

        if (current == null) {
            throw new NoSuchElementException();
        }

        return new Match(sequence, current.ch);
    }

    // returns the inverse of the coding trie
    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> lookupTable = new HashMap<>();
        // Start pre-order traversal from the root with an empty BitSequence as the initial path
        buildTableHelper(lookupTable, root, new BitSequence());
        return lookupTable;
    }

    /* Pre-order traversal helper to build the lookup table. */
    private void buildTableHelper(
            Map<Character, BitSequence> lookupTable,
            Node node,
            BitSequence path
    ) {
        // Base case: Reached a null child, backtrack
        if (node == null) {
            return;
        }
        // Leaf node found: Store the character with its corresponding path
        if (node.isLeaf()) {
            lookupTable.put(node.ch, path);
            return;
        }
        /* Each call to appended() returns a new object
         * ensuring path isolation between branches! */
        buildTableHelper(lookupTable, node.left, path.appended(0));
        buildTableHelper(lookupTable, node.right, path.appended(1));
    }
}
