package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the value mapped to by KEY in the subtree rooted in P.
     * or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int cmp = key.compareTo(p.key);
        if (cmp == 0) {
            return p.value;
        }
        if (cmp < 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /**
     * Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
     * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(p.key);
        if (cmp == 0) {
            p.value = value;
        }
        if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        } else {
            p.right = putHelper(key, value, p.right);
        }
        return p;
    }

    /**
     * Inserts the key KEY
     * If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /// ///////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        traversal(keySet, root);
        return keySet;
    }

    /* Performs an in-order traversal of the BST, adding keys to the set in ascending order. */
    private void traversal(Set<K> keySet, Node node) {
        if (node == null) {
            return;
        }
        traversal(keySet, node.left);
        keySet.add(node.key);
        traversal(keySet, node.right);
    }

    /**
     * Removes KEY from the tree if present
     * returns VALUE removed,
     * null on failed removal.
     */
    @Override
    public V remove(K key) {
        // find the target node and its parent
        Node current = root;
        Node parent = null;

        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp == 0) {
                break;
            }
            parent = current;
            current = (cmp < 0) ? current.left : current.right;
        }

        // Key not found in the tree
        if (current == null) {
            return null;
        }

        // record the value to be removed
        V removedValue = current.value;

        // remove the target node

        // Case 1: Node has zero or one child
        if (current.left == null || current.right == null) {
            Node child = (current.left != null) ? current.left : current.right;
            // Update root if target is root
            if (parent == null) {
                root = child; // Directly replace root
            } else if (parent.left == current) {
                parent.left = child; // Update parent's left
            } else {
                parent.right = child; // Update parent's right
            }
        } else { // Case 2: Node has two children
            // Find the in-order successor (min node in right subtree)
            Node minNode = current.right;
            Node minParent = current;
            while (minNode.left != null) {
                minParent = minNode;
                minNode = minNode.left;
            }

            // Copy successor's key and value to current node
            current.key = minNode.key;
            current.value = minNode.value;

            // Remove the successor
            if (minParent == current) {
                minParent.right = minNode.right; // Handle if minNode is direct right child!
            } else {
                minParent.left = minNode.right; // Link minParent to minNode's right subtree
            }
        }

        // adjust the size and return the value
        size -= 1;
        return removedValue;
    }

    /**
     * Removes the key-value entry for the specified key only if it is
     * currently mapped to the specified value.  Returns the VALUE removed,
     * null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V currentValue = get(key);
        if (currentValue != null && currentValue.equals(value)) {
            return remove(key);
        }
        return null;
    }


    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
