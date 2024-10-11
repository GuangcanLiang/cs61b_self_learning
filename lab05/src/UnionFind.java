import java.util.Arrays;

public class UnionFind {
    // TODO: Instance variables
    public int number;
    public int[] theset;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        number = N;
        theset =new int[N];
        Arrays.fill(theset, -1);
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        if (theset[v] < 0){
            return -1 * theset[v];
        }
        else {
            return sizeOf(theset[v]);
        }
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        return theset[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        int rootv1 = find(v1);
        int rootv2 = find(v2);
        if (rootv2 == rootv1){
            return true;
        }
        else {
            return false;
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        if (v >= number){
            throw new IllegalArgumentException("what you find isn't belong to the dijointset.");
        }
        int root;
        if (parent(v) < 0){
            root = v;
        }
        else {
             root = find(parent(v));
             int path = v;
             while (parent(path) > 0) {
                 int next = parent(path);
                 theset[path] = root;
                 path = next;
             }

        }
        return root;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        int rootv1 = find(v1);
        int rootv2 = find(v2);
        int sizev1 = sizeOf(v1);
        int sizev2 = sizeOf(v2);
        if (connected(v1, v2)) {
            return;
        }
        if (sizev1 == sizev2){
            theset[rootv2] = theset[rootv1] + theset[rootv2];
            theset[rootv1] = rootv2;
        } else if (sizev1 > sizev2) {
            theset[rootv1] = theset[rootv1] + theset[rootv2];
            theset[rootv2] = rootv1;
        }
        else {
            theset[rootv2] = theset[rootv1] + theset[rootv2];
            theset[rootv1] = rootv2;
        }
    }

}
