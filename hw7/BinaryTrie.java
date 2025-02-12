import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.Map;


public class BinaryTrie implements Serializable {
    // alphabet size of extended ASCII
    private static final int R = 256;

    private Node root;

    // Huffman trie node
    private static class Node implements Comparable<Node>{
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
            if (frequencyTable.get(c) > 0) {
                pq.insert(new Node(c, frequencyTable.get(c), null, null));
            }
        }

        // merge two smallest trees
        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, null, null);
            pq.insert(parent);
        }

        root = pq.delMin();
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        return null;
    }

    public Map<Character, BitSequence> buildLookupTable() {
        return null;
    }
}
