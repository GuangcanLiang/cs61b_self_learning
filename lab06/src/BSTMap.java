import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B{

    private class BSTMapNode<K extends Comparable<K>, V> {
        K key;
        V value;
        BSTMapNode left;
        BSTMapNode right;
        int size;

        BSTMapNode(K key, V value, BSTMapNode<K,V> left, BSTMapNode<K, V> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;

        }

        BSTMapNode(K key, V value) {
            this(key, value, null, null);
            int size = 1;
        }



    }


    BSTMapNode<K, V> root;

    public BSTMap() {
        root = null;
    }


    BSTMapNode put(BSTMapNode<K, V> node, K key, V value) {
        //把传入的改变了，再return出去
        if (node == null) {
            node = new BSTMapNode<K, V>(key, value);
        } else if (node.key == key) {
            node.value = value;
        } else {
            if (node.key.compareTo(key) > 0) {
                node.left = put(node.left, key, value);
            } else {
                node.right = put(node.right, key, value);
            }
        }
        return node;
    }
    
    boolean containsKey(BSTMapNode<K, V> node, K key) {
        if (node ==  null) {
            return false;
        } else if (node.key == key) {
            return true;
        } else {
            return containsKey(node.left, key) || containsKey(node.right, key);
        }
    }



    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */


    @Override
    public void put(Object key, Object value) {
        root = put(root, (K) key ,(V) value);

    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public Object get(Object key) {
        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(Object key) {
       return containsKey(root, (K)key);
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {

    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set keySet() {
        return Set.of();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public Object remove(Object key) {
        return null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator iterator() {
        return null;
    }
}