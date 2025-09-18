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

        //If the parent is the root, then we can't call methods from parent's parent without an exception
        if(parent == this.root) {
            this.root = child;
        } else {
            //Figures out which side parent's parent needs to stitch in parent's child
            if(parent.getParent().getLeft() == parent) {
                parent.getParent().setLeft(child);
            } else {
                parent.getParent().setRight(child);
            }
        }

        //If child was to the left of parent, parent will be to the right of child, and child's right subtree will become parent's left subtree
        if(parent.getLeft() == child) {
            parent.setLeft(child.getRight());

            //Checks to make sure we aren't calling methods on a null node
            if(child.getRight() != null) {
                child.getRight().setParent(parent);
            }

            child.setRight(parent);
        } else { //opposite for if child was to the right of parent
            parent.setRight(child.getLeft());

            if(child.getLeft() != null) {
                child.getLeft().setParent(parent);
            }

            child.setLeft(parent);
        }

        //Sets child's new parent
        child.setParent(parent.getParent());
        //Sets parent's new parent as the child
        parent.setParent(child);
    }

    public static void main(String[] args) {
        BSTRotation tree = new BSTRotation<>();

        System.out.println(tree.test1() ? "Test 1 passed" : "Test 1 failed");
        System.out.println(tree.test2() ? "Test 2 passed" : "Test 2 failed");
        System.out.println(tree.test3() ? "Test 3 passed" : "Test 3 failed");
    }

    //Tests left rotations, on root/non-root nodes, for 0/1/2/3 shared children
    public boolean test1() {
        boolean successful = true;
        BSTRotation<Integer> intTree = new BSTRotation<>();
        BinaryNode<Integer>[] nodes = new BinaryNode[100];
        nodes[0] = new BinaryNode<Integer>(0);
        intTree.root = nodes[0];

        for(int i = 1; i < 100; ++i) {
            nodes[i] = new BinaryNode<Integer>(i);
            intTree.insertHelper(nodes[i], intTree.root);
        }

        for(int i = 99; i > 0; --i) {
            intTree.rotate(nodes[99], nodes[i - 1]);
            successful = successful && nodes[i - 1].getParent() == nodes[99];
        }

        return successful;
    }

    //Tests left rotations, on root/non-root nodes, for 0/1/2/3 shared children
    public boolean test2() {
        boolean successful = true;
        BSTRotation<Integer> intTree = new BSTRotation<>();
        BinaryNode<Integer>[] nodes = new BinaryNode[100];
        nodes[0] = new BinaryNode<Integer>(99);
        intTree.root = nodes[0];

        for(int i = 1; i < 100; ++i) {
            nodes[i] = new BinaryNode<Integer>(99 - i);
            intTree.insertHelper(nodes[i], intTree.root);
        }

        for(int i = 99; i > 0; --i) {
            intTree.rotate(nodes[99], nodes[i - 1]);
            successful = successful && nodes[i - 1].getParent() == nodes[99];
        }

        return successful;
    }

    //Test 1 but for floats
    public boolean test3() {
        boolean successful = true;
        BSTRotation<Float> floatTree = new BSTRotation<>();
        BinaryNode<Float>[] nodes = new BinaryNode[100];
        nodes[0] = new BinaryNode<Float>(0f);
        floatTree.root = nodes[0];

        for(int i = 1; i < 100; ++i) {
            nodes[i] = new BinaryNode<Float>((float) i);
            floatTree.insertHelper(nodes[i], floatTree.root);
        }

        for(int i = 99; i > 0; --i) {
            floatTree.rotate(nodes[99], nodes[i - 1]);
            successful = successful && nodes[i - 1].getParent() == nodes[99];
        }

        return successful;
    }
}
