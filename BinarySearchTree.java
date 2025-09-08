public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T> {
    //The root node of this BinarySearchTree
    protected BinaryNode<T> root;

    //Creates a BinarySearchTree with a null root
    public BinarySearchTree() {
        root = null;
    }

    @Override
    //Given the data to insert determines first if it is non-null (i.e. valid data), and if it is fine then inserts it into the tree
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

    //This test creates 100 integer trees with a random number of right and left nodes, and inserts them in a random sequence
    //This test also verifies that the size of the trees are correct both before and after clearing
    public boolean test1() {
        //Keeps track of success across all trees at passing contains, size, and clear checks
        boolean successful = true;

        //Creates a backup access to the old root and clears the tree for testing
        BinaryNode<T> clonedRoot = this.root;
        this.clear();

        for(int i = 0; i < 100; ++i) {
            //Get how many left/right children we'll have, and create counters to keep track of how many we insert into the tree
            int left = (int) (Math.random() * 100d);
            int right = (int) (Math.random() * 100d);
            int leftCounter = 0;
            int rightCounter = 0;
            //Get the number of left children to insert before switching to right children
            int numToInsert = (int) (Math.random() * 10d);
            int insertCounter = 0;
            //Start with inserting left children
            boolean leftInsert = true;
            //Since for left children the number to insert = lastNum - 5, on average, we do that times the number of left children we're inserting
            Integer lastNum = numToInsert * 5;

            //What the size of our tree should be after we finish inserting everything
            int supposedSize = left + right;

            //While I have both left and right children to insert
            while(leftCounter < left && rightCounter < right) {
                //If inserting left, decrease lastNum and increment my left counter
                if(leftInsert) {
                    lastNum -= (int) (Math.random() * 10d);
                    ++leftCounter;
                } else {
                    //If inserting right, increase lastNum and increment my rightCounter
                    lastNum += (int) (Math.random() * 10d);
                    ++rightCounter;
                }

                //Insert lastNum
                this.insert((T) lastNum);
                //Updates success by checking that lastNum is in the tree
                successful = successful && this.contains((T) lastNum);
                //If, for this tree, lastNum is not in the tree, print what number failed
                if(!this.contains((T) lastNum)) {
                    System.out.println("Contains check failed for: " + lastNum);
                }

                //Increment the insert counter and check if we've reached the number to insert
                if(++insertCounter == numToInsert) {
                    //Flip whether we're inserting left/right children
                    leftInsert = !leftInsert;
                    //Reset the insert counter
                    insertCounter = 0;
                    //Get a new random number to insert
                    numToInsert = (int) (Math.random() * 10d);
                }
            }

            //If we run out of right children, insert the rest of the left children
            while(leftCounter++ < left) {
                lastNum -= (int) (Math.random() * 10d);
                this.insert((T) lastNum);
                successful = successful && this.contains((T) lastNum);
                if(!this.contains((T) lastNum)) {
                    System.out.println("Contains check failed for: " + lastNum);
                }
            }

            //If we run out of left children, insert the rest of the right children
            while(rightCounter++ < right) {
                lastNum += (int) (Math.random() * 10d);
                this.insert((T) lastNum);
                successful = successful && this.contains((T) lastNum);
                if(!this.contains((T) lastNum)) {
                    System.out.println("Contains check failed for: " + lastNum);
                }
            }

            //Update our success by checking if the tree's supposed size and actual size match
            successful = successful && supposedSize == this.size();
            //Print, for this tree, whether it's size check as successful
            //System.out.println(supposedSize == this.size() ? "This tree is the correct size" : "This tree is the wrong size");

            //Clear the tree and update our success by making sure its size is 0
            this.clear();
            successful = successful && this.size() == 0;
            //Print, for this tree, if it was successfully cleared
            //System.out.println(this.size() == 0 ? "The tree was successfully cleared": "The tree failed to clear"); 
        }

        //Restores the original tree by putting back the old root
        this.root = clonedRoot;

        return successful;
    }

    public boolean test2() {
        return false;
    }

    public boolean test3() {
        return false;
    }
}
