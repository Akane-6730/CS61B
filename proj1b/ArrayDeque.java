public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = items.length - 1;
        nextLast = 0;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int index = (nextFirst + 1) % items.length;
        for (int i = 0; i < size; i++) {
            a[i] = items[index];
            index = (index + 1) % items.length;
        }

        items = a;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(4 * size);
        }
        items[nextFirst] = item;
        nextFirst = (nextFirst + items.length - 1) % items.length;
        size = size + 1;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(4 * size);
        }
        items[nextLast] = item;
        nextLast = (nextLast + 1) % items.length;
        size = size + 1;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        if (isEmpty()) {
            return;
        }
        int index = (nextFirst + 1) % items.length;
        System.out.print(items[index].toString());
        for (int i = 1; i < size; i++) {
            index = (index + 1) % items.length;
            System.out.print(" " + items[index].toString());
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        int index = (nextFirst + 1) % items.length;
        T item = items[index];
        items[index] = null;
        nextFirst = index;
        size = size - 1;

        if (size < items.length / 4 && items.length >= 16) {
            resize(items.length / 2);
        }

        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        int index = (nextLast - 1 + items.length) % items.length;
        T item = items[index];
        items[index] = null;
        nextLast = index;
        size = size - 1;

        if (size < items.length / 4 && items.length >= 16) {
            resize(items.length / 2);
        }

        return item;
    }

    @Override
    public T get(int index) {
        return items[(nextFirst + index + 1) % items.length];
    }
}
