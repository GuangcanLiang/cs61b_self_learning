import java.util.ArrayList;
import java.util.List;

public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        boolean temp = node.isBlack;
        node.isBlack = node.left.isBlack;
        node.left.isBlack = temp;
        node.right.isBlack = temp;
        //交换该节点与子节点的颜色，在假设左右两边都有子树且颜色相同(事实上只有这种情况翻滚才有意义)
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        RBTreeNode<T> temp = new RBTreeNode<>(node.isBlack, node.item, node.left, node.right);//原本是直接 = node(坏了，这里直接把node给改了);//记录下待处理节点
        temp.left = temp.left.right;//待处理的左指向原左的右，(坏了，这里直接把node给改了)
        node = node.left;//改变，用待处理的左去覆盖
        node.right = temp;//覆盖后的右指向前面的处理过的记录节点
        //上面完成了旋转操作，还有颜色未改(交换！交换！颜色)
        //node.isBlack = true;
        //temp.isBlack = false;
        boolean temcolorOFtemp = temp.isBlack;
        boolean temcolorOFnode = node.isBlack;
        node.isBlack = temcolorOFtemp;
        temp.isBlack = temcolorOFnode;
        return node;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        RBTreeNode<T> temp = new RBTreeNode<>(node.isBlack, node.item, node.left, node.right);//记录下待处理节点
        temp.right = temp.right.left;//待处理的右指向原右边的左
        node = node.right;//改变，用待处理的you去覆盖
        node.left = temp;//覆盖后的右指向前面的处理过的记录节点
        //上面完成了旋转操作，还有颜色未改
        //node.isBlack = true;
        //temp.isBlack = false;
        boolean temcolorOFtemp = temp.isBlack;
        boolean temcolorOFnode = node.isBlack;
        node.isBlack = temcolorOFtemp;
        temp.isBlack = temcolorOFnode;
        return node;//有问题啊，这里的旋转只能是特定的形状作特定的旋转(好像又没有错，淦)
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }//这是什么意思? 一个封装?

    /**
     * Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // TODO: Insert (return) new red leaf node.
        RBTreeNode<T> hadInseretedNode;//最后return这个出去
        if (node == null) {
            hadInseretedNode =  new RBTreeNode(false, item);
        }

        // TODO: Handle normal binary search tree insertion.
        /*else {
            if (item.compareTo(node.item) > 0) {
                return new RBTreeNode<>(node.isBlack, node.item, node.left, insert(node.right, item));
            }
            else {
                return new RBTreeNode<>(node.isBlack, node.item, insert(node.left, item), node.right);
            }
        }*/
        else {

            hadInseretedNode = new RBTreeNode(node.isBlack, node.item);
            if (item.compareTo(node.item) > 0) {
                hadInseretedNode.left = node.left;
                hadInseretedNode.right = insert(node.right, item);
            } else {
                hadInseretedNode.left = insert(node.left, item);
                hadInseretedNode.right = node.right;
            }


            // TODO: Rotate left operation
            if (item.compareTo(hadInseretedNode.item) > 0 && !isRed(hadInseretedNode.left)) {
                hadInseretedNode = rotateLeft(hadInseretedNode);
            }

            // TODO: Rotate right operation
            if (isRed(hadInseretedNode.left.left) && isRed(hadInseretedNode.left)) {
                hadInseretedNode = rotateRight(hadInseretedNode);

            }

            // TODO: Color flip
            if (isRed(hadInseretedNode.left) && isRed(hadInseretedNode.right)) {
               flipColors(hadInseretedNode);
            }

        }
         return hadInseretedNode;//fix this return statement
    }

}
