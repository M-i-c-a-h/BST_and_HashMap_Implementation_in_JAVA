import java.util.Iterator;

public class BinarySearchTreeDict<K extends Comparable<K>,V> implements ProjOneDictionary<K,V> {

    private BST_Node root = null;
    private int size;

    /**
     * BST structure insert a Node to left/right if grt/less
     * key the at the BST Node
     * value -> value held by key
     * parent-> parent Node
     * left -> left child of Node
     * right -> right child of Node
     */
    private class BST_Node{
        K key;
        V value;
        BST_Node parent = null;
        BST_Node left = null;
        BST_Node right = null;

        BST_Node(K newKey, V newValue){
            super();
            key = newKey;
            value = newValue;
        }
    }
    private class BST_NodeIterator implements Iterator<K>{
        private BST_Node curr = null;
        BST_NodeIterator(){
            if(root != null) {
                // find the leftmost element
                curr = leftMostNode(root);

            }
        }
        private BST_Node findRightMin(BST_Node right){
            while(right.left != null){
                right = right.left;
            }
            return right;
        }
        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public K next() {
            K keyToRet = curr.key;
            // If the right subtree of the node is not empty, then the next inorder node lies in the right subtree.
            // Go to the right subtree and return the node with the minimum priority in the right subtree.
            if(curr.right != null){
                curr = findRightMin(curr.right);
            }
            else{
                // if curr does not have a right node move up to parent node
                while(curr.parent != null && curr == curr.parent.right ){
                    curr = curr.parent;
                }
                // advance curr to parent or null if at root node
                curr = curr.parent;
            }
            return keyToRet;
        }
    }
    @Override
    public boolean insert(K key, V value) throws NullValueException {
        if(value == null){ throw new NullValueException();}

        // determine if key exist in BST
        boolean found = (this.find(key) == value);

        // create a newNode to be inserted
        BST_Node newNode = new BST_Node(key, value);
        // call helper insert function
        insertNode(newNode);

        // if a new node was added increase size
        if(!found){
            size++;
        }
        return found;
    }

    @Override
    public V find(K key) {
        BST_Node toRet = searchBST(root, key);
        if(toRet == null){ return null;}
        return toRet.value;
    }

    @Override
    public boolean delete(K key) {
        if(this.find(key) == null){
            return false;
        }
        // do something
        BST_Node nodeToDelete = searchBST(root, key);

        // if node to be deleted is the root of BST,
        // update root node
        if(nodeToDelete == root){
            // if root node has a right node,
            // right node is the new node of BST
            if(nodeToDelete.right != null){
                // if root to be deleted has a left subtree,
                // move left subtree to right subtree
                if(nodeToDelete.left != null){
                    moveLeftToRight(nodeToDelete.left, nodeToDelete.right);
                }
                //  delete node
                root = nodeToDelete.right;
            }
            else{
                root = nodeToDelete.left;
            }
        }
        // if node to be deleted is a leaf node,
        // call helper function to delete node
        else if(isLeafNode(nodeToDelete)){
            deleteLeafNode(nodeToDelete);
        }
        // if node has a parent node and leaf nodes
        //
        else{
            if(nodeToDelete.right != null){
                // if node to be deleted has a left subtree,
                // move left subtree to right subtree
                if(nodeToDelete.left != null){
                    moveLeftToRight(nodeToDelete.left, nodeToDelete.right);
                }
                //  delete node
                nodeToDelete.right.parent = nodeToDelete.parent;
                // update parent leaf node
                //Todo********
                if(nodeToDelete.parent.left == nodeToDelete){
                    //nodeToDelete.parent.left = nodeToDelete.left;
                }
                else if(nodeToDelete.parent.right == nodeToDelete){
                    nodeToDelete.parent.right = nodeToDelete.right;
                }
            }
            else{
                nodeToDelete.left.parent = nodeToDelete.parent;
                if(nodeToDelete.parent.left == nodeToDelete){
                    nodeToDelete.parent.left = null;
                }
                //Todo********
                else{
                    //nodeToDelete.parent.right = nodeToDelete.left;
                }

            }
        }
        size--;
        return true;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Iterator<K> iterator() {
        return new BST_NodeIterator();
    }
    // recursive helper function to search BST
    private BST_Node searchBST(BST_Node root, K key){
        // base case: if root is null return
        if(root == null){
            return null;
        }
            // case 1: if current root->key > key search left of tree
        if(root.key.compareTo(key) > 0){
            return searchBST(root.left, key);
            // case 2: if current root->key < key search right of tree
        }else if(root.key.compareTo(key) < 0){
            return searchBST(root.right, key);
        }

        return root;
    }

    /**
     *
     * @param newNode is new Node to be added to tree
     * function -> overwrites existing nodes with key equal to new node key
     *          -> inserts new node to left of tree if node key is less than root
     *          -> inserts new node to right of tree if node key is greater than root
     *          -> has no return type
     */
    private void insertNode(BST_Node newNode){

        if(root == null){
            root = newNode;
        }
        else{
            // node to traverse BST
            BST_Node currNode = root;

            while(currNode != null){
                // if overwrite node key with equal to newNode key
                if(currNode.key.compareTo(newNode.key) == 0){
                    currNode.value = newNode.value;
                    currNode = null;    // end loop
                }
                // if newNode key is less than current node key,
                // insertion should be on left subtree
                else if(currNode.key.compareTo(newNode.key) > 0){
                    // if currNode has no left child,
                    // insert newNode to left of current node
                    if(currNode.left == null){
                        currNode.left = newNode;
                        newNode.parent = currNode;      // update parent node
                        currNode = null;    // end loop
                    }
                    // advance lower
                    else{
                        currNode = currNode.left;
                    }
                }
                // if newNode key is greater than current node key,
                // insertion should be on the right subtree
                else{
                    // if current node has no right child,
                    // insert newNode to right of current node
                    if(currNode.right == null){
                        currNode.right = newNode;
                        newNode.parent = currNode;      // update parent node
                        currNode = null;    // end loop
                    }
                    // advance higher
                    else{
                        currNode = currNode.right;
                    }
                }
            }
        }
    }
    /**
     * determines if current node is a leaf node
     * a leaf node -> is a BST_Node with left and right equal to null
     * @param root is current node inspected
     * @return returns true if BST_Node is a leaf node
     */
    private boolean isLeafNode(BST_Node root){ return (root.left == null) && (root.right == null);}

    /**
     * returns leftMost Node
     * @param root is current node inspected
     * @return returns leftMost Node
     */
    private BST_Node leftMostNode(BST_Node root){
        BST_Node current = root;
        if(current == null){
            return null;
        }
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    /**
     * Moves left subtree to right subtree
     * @param leftSubTree is the left subtree to be moved to the right subtree
     * @param rightSubTree is the proposed destination of leftSubTree
     *                     if rightSubTree->left is not null,
     *                     go lower left until rightSubTree->left is null
     */
    private void moveLeftToRight(BST_Node leftSubTree, BST_Node rightSubTree){
        BST_Node leftMostRight = leftMostNode(rightSubTree);
        leftSubTree.parent = leftMostRight;
        if (leftMostRight != null) {
            leftMostRight.left = leftSubTree;
        }
    }
    /**
     * Deletes a leafNode
     * @param root is current node inspected
     */
    private void deleteLeafNode(BST_Node root){
        // if current node is a left leaf
        if(root == root.parent.left){
            root.parent.left = null;
        }
        // if current node is a right leaf
        else{
            root.parent.right = null;
        }
    }
}

