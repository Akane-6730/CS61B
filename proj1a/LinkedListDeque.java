public class LinkedListDeque<T> {

    private Node sentinel;
    private int size;

    public class Node {
	public Node prev;
	public T item;
	public Node next;

	/** Constructor for Node */
	public Node(Node prev, T item, Node next) {
	    this.prev = prev;
	    this.item = item;
	    this.next = next;
	}
    }

    /** Create an empty linked list deque. */
    public LinkedListDeque() {
	size = 0;
	sentinel = new Node(null, null, null);
	sentinel.next = sentinel.prev = sentinel;
    }


    public boolean isEmpty() {
	if (sentinel.next == sentinel) {
	    return true;
	}
	return false;
    }

    public int size() {
	return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.*/
    public void printDeque() {
	Node p = sentinel;
	while(p.next != sentinel) {
	    if (p == sentinel) {
		System.out.printf(p.next.item.toString());
	    } else {
		System.out.printf(" " + p.next.item.toString());
	    }
	    p = p.next;
	}
    }

    /**
     * Add and remove operations must not involve any looping or recursion.
     * A single such operation must take “constant time”.
     */

    /** Add functions */
    public void addFirst(T item) {
	size += 1;

	if (sentinel.next == sentinel) {
	    sentinel.next = sentinel.prev = new Node(sentinel, item, sentinel);
	    return ;
	}
	/** Insert */
	sentinel.next = new Node(sentinel, item, sentinel.next);
	/** Update the sentinel */
	sentinel.next.next.prev = sentinel.next;
    }

    public void addLast(T item) {
	size += 1;

	if (sentinel.prev == sentinel) {
	    sentinel.next = sentinel.prev = new Node(sentinel, item, sentinel);
	    return ;
	}
	/** Insert */
	sentinel.prev = new Node(sentinel.prev, item, sentinel);
	/** Update the sentinel */
	sentinel.prev.prev.next = sentinel.prev;
    }

    /** Remove functions */
    public T removeFirst() {
	/** If no such item exists*/
	if (sentinel.next == sentinel) {
	    return null;
	}

	sentinel.next = sentinel.next.next;
	sentinel.next.prev = sentinel;

	return sentinel.next.item;
    }

    public T removeLast() {
	/** If no such item exists*/
	if (sentinel.prev == sentinel) {
	    return null;
	}

	sentinel.prev = sentinel.prev.prev;
	sentinel.prev.next = sentinel;

	return sentinel.prev.item;
    }

    /**
    * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
    * If no such item exists, returns null. Must not alter the deque!
    * get must use iteration, not recursion.
    */
    public T get(int index) {
	if (index >= this.size() || index < 0) {
	    return null;
	}

	Node p = sentinel;

	for (int i = 0; i <= index; i++) {
	    p = p.next;
	}

	return p.item;
    }

    /** Maybe getRecursive can't be done without helper? */
    private Node getRecursivehelper(int index) {
	if (index >= this.size() || index < 0) {
	    return null;
	}

	if (index == 0) {
	    return sentinel.next;
	}

	return getRecursivehelper(index - 1).next;
    }

    public T getRecursive(int index) {
	if (index >= this.size() || index < 0) {
	    return null;
	}

	return getRecursivehelper(index).item;
    }
}
