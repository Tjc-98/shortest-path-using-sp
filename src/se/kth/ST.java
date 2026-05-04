package se.kth;

import java.util.NoSuchElementException;

class ST<Key extends Comparable<Key>, Value> {
    private static final int INIT_CAPACITY = 2;
    private Key[] keys;
    private Value[] values;
    private int size = 0;

    public ST() {
        this(INIT_CAPACITY);
    }

    /**
     * initialize the table with a specific capacity.
     * @param capacity the desired capacity.
     */
    public ST(int capacity) {
        this.keys = (Key[]) new Comparable[capacity];
        this.values = (Value[]) new Object[capacity];
    }

    /**
     * Get the value associated to the given key.
     * @param key the key paired to the value.
     * @return the value of associated key.
     * @throws IllegalArgumentException if the key is invalid.
     */
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is invalid.");
        }
        if (isEmpty()) {
            return null;
        }
        int i = rank(key);
        if (i < this.size && this.keys[i].compareTo(key) == 0) {
            return this.values[i];
        }
        return null;
    }

    /**
     * Add the key and the
     * @param key The key wanted to be added with its value.
     * @param value the value associated with the key.
     * @throws IllegalArgumentException if the key is invalid.
     */
    public void put(Key key, Value value)  {
        if (key == null) {
            throw new IllegalArgumentException("The key is invalid.");
        }
        if (value == null) {
            delete(key);
            return;
        }

        int i = rank(key);
        if (i < this.size && this.keys[i].compareTo(key) == 0) {
            this.values[i] = value;
            return;
        }
        if (this.size == this.keys.length) {
            resize(2 * this.keys.length);
        }

        // Shift
        for (int j = this.size; j > i; j--)  {
            this.keys[j] = this.keys[j-1];
            this.values[j] = this.values[j-1];
        }
        this.keys[i] = key;
        this.values[i] = value;
        this.size++;

        assert check();
    }


    /**
     * Check if the table is empty or not.
     * @return true or false depending on the condition.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Get the size of the table.
     * @return the value of the size.
     */
    public int size() {
        return this.size;
    }

    /**
     * Check the correct placement for the key given.
     * @param key the key wanted to check the placement of.
     * @return the index of the key.
     * @throws IllegalArgumentException if the key is invalid.
     */
    public int rank(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("The key to compare is invalid.");
        }

        int lo = 0, hi = this.size - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int compareResult = key.compareTo(keys[mid]);
            if (compareResult < 0) {
                hi = mid - 1;
            } else if (compareResult > 0) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return lo;
    }

    private void resize(int capacity) {
        assert(capacity >= this.size);
        Key[]   tempKeys = (Key[])   new Comparable[capacity];
        Value[] tempValues = (Value[]) new Object[capacity];
        for (int i = 0; i < this.size; i++) {
            tempKeys[i] = this.keys[i];
            tempValues[i] = this.values[i];
        }
        this.values = tempValues;
        this.keys = tempKeys;
    }

    /**
     * Remove the specified key from the table.
     * @param key the key that wanted to be removed.
     * @throws IllegalArgumentException if the key is invalid.
     */
    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is invalid.");
        }
        if (isEmpty()) {
            return;
        }

        int i = rank(key);
        if (i == this.size || this.keys[i].compareTo(key) != 0) {
            return;
        }

        // Shift
        for (int j = i; j < this.size - 1; j++)  {
            this.keys[j] = this.keys[j+1];
            this.values[j] = this.values[j+1];
        }

        this.size--;
        this.keys[this.size] = null;
        this.values[this.size] = null;

        // resize if 1/4 full
        if (this.size > 0 && this.size == (this.keys.length / 4)) {
            resize(keys.length / 2);
        }

        assert check();
    }

    /**
     * Get the key in the specific placement.
     * @param k the index in which the key is placed.
     * @return the key.
     * @throws IllegalArgumentException if the key is invalid.
     */
    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("The kth value is invalid." + k);
        }
        return keys[k];
    }

    /**
     * Iterable of the table.
     * @return a queue with all the keys in it.
     */
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    /**
     * Iterable to iterate over the table.
     * @param loKey the minimum index.
     * @param hiKey the maximum index.
     * @return queue with the keys in the range.
     * @throws IllegalArgumentException if the key is invalid.
     */
    public Iterable<Key> keys(Key loKey, Key hiKey) {
        if (loKey == null) {
            throw new IllegalArgumentException("The key lo is invalid.");
        }
        if (hiKey == null) {
            throw new IllegalArgumentException("The key hi is invalid.");
        }

        Queue<Key> queue = new Queue<Key>();
        if (loKey.compareTo(hiKey) > 0) {
            return queue;
        }
        for (int i = rank(loKey); i < rank(hiKey); i++) {
            queue.enqueue(keys[i]);
        }
        if (contains(loKey)) {
            queue.enqueue(keys[rank(hiKey)]);
        }
        return queue;
    }

    /**
     * Get the minimum key present in the table.
     * @return the smallest key.
     * @throws NoSuchElementException if the table is empty.
     */
    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("The table is empty.");
        }
        return keys[0];
    }

    /**
     * Get the maximum key present in the table.
     * @return The largest key.
     * @throws NoSuchElementException if the table is empty.
     */
    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("The table is empty.");
        }
        return keys[this.size - 1];
    }

    /**
     * Check if the passed key is present in the table.
     * @param key the key that wanted to check its existent in the table.
     * @return true or false depending on the condition.
     * @throws IllegalArgumentException if the key is invalid.
     */
    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is invalid.");
        }
        return get(key) != null;
    }

    /***************************************************************************
     *  Check internal invariants.
     ***************************************************************************/

    private boolean check() {
        return isSorted() && rankCheck();
    }

    // are the items in the array in ascending order?
    private boolean isSorted() {
        for (int i = 1; i < size(); i++)
            if (this.keys[i].compareTo(this.keys[i - 1]) < 0) return false;
        return true;
    }

    // check that rank(select(i)) = i
    private boolean rankCheck() {
        for (int i = 0; i < size(); i++) {
            if (i != rank(select(i))) {
                return false;
            }
        }
        for (int i = 0; i < size(); i++) {
            if (this.keys[i].compareTo(select(rank(this.keys[i]))) != 0) {
                return false;
            }
        }
        return true;
    }
}