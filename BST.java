import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST
     */
    public BST() {
        root = null;
    }

    /**
     * Initializes the BST with the data in the collection. The data in the BST
     * should be added in the same order it is in the collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Not legal.");
        }
        for (int i = 0; i < data.size(); i++) {
            if (data.toArray()[i] == null) {
                throw new java.lang.IllegalArgumentException("Not legal.");
            }
            add((T) (data.toArray()[i]));
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Not legal.");
        }
        root = addHelper(root, data);
    }

    /**
     * Helper method for add that adds data to the appropriate place
     *
     * @param current the node you are currently on
     * @param data the data to add to the tree
     * @return the modified tree with the node that was added to the tree
     */
    private BSTNode<T> addHelper(BSTNode<T> current, T data) {
        if (current == null) {
            size++;
            current = new BSTNode<T>(data);
            return current;
        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(addHelper(current.getLeft(), data));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(addHelper(current.getRight(), data));
        }
        return current;
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Not legal.");
        }
        size--;
        return removeHelper(root, null, data);
    }

    /**
     * Helper method for remove that removes data from the tree
     *
     * @param current the node you are currently on
     * @param parent the node you were previously on
     * @param data the data to remove from the tree
     * @return the data of the node that was removed
     */
    private T removeHelper(BSTNode<T> current, BSTNode<T> parent, T data) {
        if (current == null) {
            throw new java.util.NoSuchElementException("Not legal.");
        }
        int compare = data.compareTo(current.getData());
        if (compare == 0) {
            T toReturn = current.getData();
            if (current.getRight() == null) {
                if (parent == null) {
                    root = current.getLeft();
                } else if (parent.getLeft() != null
                    && parent.getLeft().equals(current)) {
                    parent.setLeft(current.getLeft());
                } else {
                    parent.setRight(current.getLeft());
                }
                return toReturn;
            } else if (current.getLeft() == null) {
                if (parent == null) {
                    root = current.getRight();
                } else if (parent.getLeft() != null
                    && parent.getLeft().equals(current)) {
                    parent.setLeft(current.getRight());
                } else {
                    parent.setRight(current.getRight());
                }
                return toReturn;
            }
            T replacement = replaceSuccessor(current.getRight(), current,
                current);
            if (replacement == null) {
                if (parent == null) {
                    root = null;
                } else if (parent.getLeft() != null
                    && parent.getLeft().equals(current)) {
                    parent.setLeft(null);
                } else {
                    parent.setRight(null);
                }
            } else {
                current.setData(replacement);
            }
            return toReturn;
        } else if (data.compareTo(current.getData()) < 0) {
            return removeHelper(current.getLeft(), current, data);
        } else {
            return removeHelper(current.getRight(), current, data);
        }
    }

    /**
     * Helper method for replacing the successor
     *
     * @param current the node you are currently on
     * @param parent the node you were previously on
     * @param deleteNode the node you want to delete
     * @return the data of the successor
     */
    private T replaceSuccessor(BSTNode<T> current, BSTNode<T> parent,
        BSTNode<T> deleteNode) {
        if (current == null) {
            return null;
        }
        if (current.getLeft() == null) {
            if (parent.equals(deleteNode)) {
                parent.setRight(current.getRight());
                return current.getData();
            }
            parent.setLeft(current.getRight());
            return current.getData();
        } else {
            return replaceSuccessor(current.getLeft(), current, deleteNode);
        }
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Not legal.");
        }
        return getHelper(root, data);
    }

    /**
     * Helper method for get that gets data from the tree
     *
     * @param current the node you are currently on
     * @param data the data to get from the tree
     * @return the data in the tree
     */
    private T getHelper(BSTNode<T> current, T data) {
        if (current == null) {
            throw new java.util.NoSuchElementException("Not legal.");
        }
        if (data.compareTo(current.getData()) < 0) {
            return getHelper(current.getLeft(), data);
        } else if (data.compareTo(current.getData()) > 0) {
            return getHelper(current.getRight(), data);
        }
        return current.getData();
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Not legal.");
        }
        return containsHelper(root, data);
    }

    /**
     * Helper method for contains that removes data from the tree
     *
     * @param current the node you are currently on
     * @param data the data to check if it is in the tree
     * @return whether or not data is in the tree
     */
    private boolean containsHelper(BSTNode<T> current, T data) {
        if (current == null) {
            return false;
        }
        if (data.compareTo(current.getData()) < 0) {
            return containsHelper(current.getLeft(), data);
        } else if (data.compareTo(current.getData()) > 0) {
            return containsHelper(current.getRight(), data);
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        List<T> preorder = new ArrayList<T>();
        preorderHelper(root, preorder);
        return preorder;
    }

    /**
     * Helper method for preorder that removes data from the tree
     *
     * @param current the node you are currently on
     * @param preorder the list for preorder traversal
     */
    private void preorderHelper(BSTNode<T> current, List<T> preorder) {
        if (current == null) {
            return;
        }
        preorder.add(current.getData());
        preorderHelper(current.getLeft(), preorder);
        preorderHelper(current.getRight(), preorder);
    }

    @Override
    public List<T> postorder() {
        List<T> postorder = new ArrayList<T>();
        postorderHelper(root, postorder);
        return postorder;
    }

    /**
     * Helper method for postorder that removes data from the tree
     *
     * @param current the node you are currently on
     * @param postorder the list for postorder traversal
     */
    private void postorderHelper(BSTNode<T> current, List<T> postorder) {
        if (current == null) {
            return;
        }
        postorderHelper(current.getLeft(), postorder);
        postorderHelper(current.getRight(), postorder);
        postorder.add(current.getData());
    }

    @Override
    public List<T> inorder() {
        List<T> inorder = new ArrayList<T>();
        inorderHelper(root, inorder);
        return inorder;
    }

    /**
     * Helper method for inorder that removes data from the tree
     *
     * @param current the node you are currently on
     * @param inorder the list for inorder traversal
     */
    private void inorderHelper(BSTNode<T> current, List<T> inorder) {
        if (current == null) {
            return;
        }
        inorderHelper(current.getLeft(), inorder);
        inorder.add(current.getData());
        inorderHelper(current.getRight(), inorder);
    }

    @Override
    public List<T> levelorder() {
        List<T> levelorder = new ArrayList<T>();
        List<BSTNode<T>> queue = new ArrayList<BSTNode<T>>();
        levelorderHelper(root, levelorder, queue);
        return levelorder;
    }

    /**
     * Helper method for levelorder that removes data from the tree
     *
     * @param current the node you are currently on
     * @param levelorder the list for levelorder traversal
     * @param queue the list for storing the nodes in order
     */
    private void levelorderHelper(BSTNode<T> current, List<T> levelorder,
        List<BSTNode<T>> queue) {
        if (current == null) {
            return;
        }
        queue.add(current);
        while (!queue.isEmpty()) {
            current = queue.remove(0);
            levelorder.add(current.getData());
            if (current.getLeft() != null) {
                queue.add(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.add(current.getRight());
            }
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        return heightHelper(root);
    }

    /**
     * Helper method for height that removes data from the tree
     *
     * @param current the node you are currently on
     * @return the height of the node
     */
    private int heightHelper(BSTNode<T> current) {
        if (current == null) {
            return -1;
        } else {
            return Math.max(heightHelper(current.getLeft()),
                heightHelper(current.getRight())) + 1;
        }
    }

    @Override
    public int depth(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Not legal.");
        }
        if (!contains(data)) {
            return -1;
        }
        return depthHelper(root, data, 1);
    }

    /**
     * Helper method for depth that removes data from the tree
     *
     * @param current the node you are currently on
     * @param data the data you are trying to find
     * @param level the current level you are at
     * @return the depth of the node found, if any
     */
    private int depthHelper(BSTNode<T> current, T data, int level) {
        if (!current.getData().equals(data)) {
            if (data.compareTo(current.getData()) < 0) {
                return depthHelper(current.getLeft(), data, level++) + 1;
            } else if (data.compareTo(current.getData()) > 0) {
                return depthHelper(current.getRight(), data, level++) + 1;
            }
        }
        return 1;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        return root;
    }
}