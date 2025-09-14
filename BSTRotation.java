public class BSTRotation<T extends Comparable<T>> extends BinarySearchTree<T> {
    public BSTRotation() {
        super();
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a right
     * child of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this
     * method will either throw a NullPointerException: when either reference is
     * null, or otherwise will throw an IllegalArgumentException.
     *
     * @param child is the node being rotated from child to parent position 
     * @param parent is the node being rotated from parent to child position
     * @throws NullPointerException when either passed argument is null
     * @throws IllegalArgumentException when the provided child and parent
     *     nodes are not initially (pre-rotation) related that way
     */
    protected void rotate(BinaryNode<T> child, BinaryNode<T> parent) throws NullPointerException, IllegalArgumentException {
        if(child == null || parent == null) {
            throw new NullPointerException();
        } else if(child.getParent() != parent || (parent.getLeft() != child && parent.getRight() != child)) {
            throw new IllegalArgumentException();
        }

        rotateHelper(child, parent);
    }

    private void rotateHelper(BinaryNode<T> child, BinaryNode<T> parent) {

    }

    public static void main(String[] args) {
        BSTRotation tree = new BSTRotation<>();

        System.out.println(tree.test1() ? "Test 1 passed" : "Test 1 failed");
        System.out.println(tree.test2() ? "Test 2 passed" : "Test 2 failed");
        System.out.println(tree.test3() ? "Test 3 passed" : "Test 3 failed");
    }

    public boolean test1() {
        return false;
    }

    public boolean test2() {
        return false;
    }

    public boolean test3() {
        return false;
    }
}