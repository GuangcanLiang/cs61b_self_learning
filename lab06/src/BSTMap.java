import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B{

    private class BSTMapNode<K extends Comparable<K>, V> {
        K key;
        V value;
        BSTMapNode left;
        BSTMapNode right;
        int size = 0;


        BSTMapNode(K key, V value, BSTMapNode<K,V> left, BSTMapNode<K, V> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;

        }

        BSTMapNode(K key, V value) {
            this(key, value, null, null);
        }



    }


    BSTMapNode<K, V> root;


    public BSTMap() {
        root = null;
    }


    private BSTMapNode put(BSTMapNode<K, V> node, K key, V value) {
        //把传入的改变了，再return出去
        if (!containsKey(node, key)) {
            if (node == null) {
                node = new BSTMapNode<K, V>(key, value);
                node.size = node.size + 1;
            } else {
                if (node.key.compareTo(key) > 0) {
                    node.left = put(node.left, key, value);
                } else {
                    node.right = put(node.right, key, value);
                }
                node.size = node.size + 1;
            }
        }
        else {//本就有，更改值
            if (node.key.equals(key)) {
                node.value = value;
            }
            else {
                if (node.key.compareTo(key) > 0) {
                    node.left = put(node.left, key, value);
                }
                else {
                    node.right = put(node.right, key, value);
                }
            }

        }
        return node;
    }
    
    private boolean containsKey(BSTMapNode<K, V> node, K key) {
        if (node ==  null) {
            return false;
        } else if (node.key.equals(key)) {
            return true;
        } else {
            return containsKey(node.left, key) || containsKey(node.right, key);
        }
    }

    private V get(BSTMapNode<K, V> node, K key) {
        if (!containsKey(node, key)) {
            return null;
        }
        if (node.key.equals(key)) {
            return node.value;
        }
        else {
            if (node.key.compareTo(key) > 0) {
                return (V)get(node.left, key);

            }
            else {
                return (V)get(node.right, key);
            }
        }
    }

    private BSTMapNode<K, V> findNode(BSTMapNode<K, V> node, K key) {
        if (node.key.equals(key)) {
            return  node;
        } else if (containsKey(node.left, key)) {
            return findNode(node.left, key);
        } else  {
            return findNode(node.right, key);
        }
    }

    /**
     * 找到一棵树最大的节点, !!!并进行修改!!!
     * 注意！！！一个函数是不能够这么写的，不能既return, 还修改！！！
     * @param node
     * @return
     */
    private BSTMapNode<K, V> findMax(BSTMapNode<K,V> node) {
        if (node.right.right == null) {
            BSTMapNode<K,V> Max = new BSTMapNode<K, V>((K) node.right.key, (V) node.right.value, node.right.left, node.right.right);
            node.right = node.right.left;
            return Max;
        } else {
            return findMax(node.right);
        }
    }

    private BSTMapNode<K, V> findMin(BSTMapNode<K,V> node) {
        if (node.left == null) {
            return node;
        } else {
            return findMin(node.left);
        }
    }
//这逼代码实在是太丑了
    private BSTMapNode<K, V> remove(BSTMapNode<K, V> node, K key) {
        if (node.key.equals(key)) {//node是要删的节点
            if (node.left == null && node.right == null) {//要删的两边都没有值
                node.size = node.size - 1;
                return null;
            } else if (node.left == null && node.right != null) {//要删的只在右边有值
                node.size = node.size - 1;
                return node.right;
            } else if (node.right == null && node.left != null) {//要删的只在左边有值
                node.size = node.size - 1;
                return node.left ;
            } else {//要删的两边都有值，在中间

                if (node.left.right == null) {
                    node.key = (K)node.left.key;
                    node.value = (V)node.left.value;
                    node.left = node.left.left;
                    node.size = node.size - 1;
                    return node;
                } else{ BSTMapNode<K,V> MAX = findMax(node.left);
                node.key = MAX.key;
                node.value = MAX.value;
                node.size = node.size - 1;
                return node;}

                /*
                BSTMapNode<K, V> minNode = findMin(node.right);
                node.key = minNode.key;
                node.value = minNode.value;
                node.right = remove(node.right, key);
                node.size = node.size - 1;
                return node;
                */}

        } else if (node.key.compareTo(key) > 0) {
            node.left = remove(node.left, key);
            node.size = node.size - 1;
            return node;
        } else {
            node.right = remove(node.right, key);
            node.size = node.size - 1;
            return node;
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
        return get(root, (K)key);
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
        if (root == null) {return 0;};
        return root.size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root = null;

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
       if(containsKey(key)) {V value = (V)get(key);
        root = remove(root,(K) key);
        return value;}
       else {
           return null;
       }
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
