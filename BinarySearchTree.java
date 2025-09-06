public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T> {
    //The root node of this BinarySearchTree
    protected BinaryNode<T> root;

    //Creates a BinarySearchTree with a null root
    public BinarySearchTree() {
        root = null;
    }

    @Override
    //Given the data to insert determines first if it is non-null (i.e. valid data), and if it is fine
    //then inserts it into the tree
    public void insert(T data) throws NullPointerException {
        //If data is null, throw an exception since we can't add non-comparable data to a tree
        if(data == null) {
            throw new NullPointerException();
        }

        //Creates a node representing the data to be added
        BinaryNode<T> newNode = new BinaryNode<>(data);

        //If the tree is empty, then set root to the newNode
        if(this.isEmpty()) {
            root = newNode;
            return;
        }
        
        //Otherwise, call on insert helper to find out where to insert the newNode
        insertHelper(newNode, root);
    }

    /**
     * Performs the naive binary search tree insert algorithm to recursively
     * insert the provided newNode (which has already been initialized with a
     * data value) into the provided tree/subtree. When the provided subtree
     * is null, this method does nothing. 
     */
    protected void insertHelper(BinaryNode<T> newNode, BinaryNode<T> subtree) {
        BinaryNode<T> subtreeRoot = null;

        //Checks whether newNode is less than or equal to current node
        if(newNode.getData().compareTo(subtree.getData()) <= 0d) {
            //Checks to see if the next left node is null
            if(subtree.getLeft() == null) {
                subtree.setLeft(newNode);
            } else {
                //If the next left node is not null, then set it as the next subtree's root
                subtreeRoot = subtree.getLeft();
            }
        } else {
            //Repeating the same as the above if-else, but for the case where newNode is greater
            if(subtree.getRight() == null) {
                subtree.setRight(newNode);
            } else {
                subtreeRoot = subtree.getRight();
            }
        }

        //If the next subtree's root is null, then we know the newNode will be the root of that subtree
        if(subtreeRoot == null) {
            //Correctly links up newNode with the rest of the nodes in the tree
            newNode.parent = subtree;
        } else {
            //Otherwise, we have to traverse deeper into the tree to find the correct insertion point
            insertHelper(newNode, subtreeRoot);
        }
    }

    @Override
    public boolean contains(Comparable<T> data) throws NullPointerException {
        //If the data provided is null, throw an exception
        if(data == null) {
            throw new NullPointerException();
        }

        //Call on the containsHelper method
        return containsHelper(data, root);
    }

    //Determines if the subtree node is null (data was not in tree), if it is equal to data, or if we have to go further in the tree to determine if data is contained
    protected boolean containsHelper(Comparable<T> data, BinaryNode<T> subtree) {
        if(subtree == null) {
            return false;
        } else if(data.equals(subtree.getData())) {
            return true;
        } else if(data.compareTo(subtree.getData()) < 0d) {
            return containsHelper(data, subtree.getLeft());
        } else {
            return containsHelper(data, subtree.getRight());
        }
    }

    @Override
    public int size() {
        return sizeHelper(root);
    }

    protected int sizeHelper(BinaryNode<T> subtree) {
        //Checks to make sure the current subtree node is actually part of the tree
        if(subtree == null) {
            return 0;
        } else {
            //Adds the current node (1) with the left child's subtree size and the right child's subtree size
            return 1 + sizeHelper(subtree.getLeft()) + sizeHelper(subtree.getRight());
        }
    }

    @Override
    public boolean isEmpty() {
        //Due to how the insert method works, if root is null then the newNode becomes root. Therefore we need only check if we have a root to know if the tree is empty
        return root == null;
    }

    @Override
    public void clear() {
        //Can just set root to null to effectively reset the tree, garbage collect takes care of the other nodes
        root = null;
    }

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree<>();

        System.out.println(tree.test1() ? "Test 1 passed" : "Test 1 failed");
        System.out.println(tree.test2() ? "Test 2 passed" : "Test 2 failed");
        System.out.println(tree.test3() ? "Test 3 passed" : "Test 3 failed");
    }

    protected boolean test1() {
        return false;
    }

    protected boolean test2() {
        return false;
    }

    protected boolean test3() {
        return false;
    }
}
