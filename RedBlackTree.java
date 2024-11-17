public class RedBlackTree {
    private Node root;
    private Node NIL;

    public RedBlackTree() {
        NIL = new Node(null);
        NIL.isRed = false;
        root = NIL;
    }

    public void insert(Product product) {
        if (search(product.getId()) != null) {
            throw new IllegalArgumentException("Product with ID " + product.getId() + " already exists!");
        }

        Node node = new Node(product);
        node.left = NIL;
        node.right = NIL;

        Node y = null;
        Node x = root;

        while (x != NIL) {
            y = x;
            if (node.data.getId().compareTo(x.data.getId()) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.data.getId().compareTo(y.data.getId()) < 0) {
            y.left = node;
        } else {
            y.right = node;
        }

        fixInsert(node);
    }

    private void fixInsert(Node k) {
        while (k != root && k.parent != null && k.parent.isRed) {
            if (k.parent == k.parent.parent.right) {
                Node u = k.parent.parent.left;
                if (u != null && u.isRed) {
                    u.isRed = false;
                    k.parent.isRed = false;
                    k.parent.parent.isRed = true;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    if (k.parent != null && k.parent.parent != null) {
                        k.parent.isRed = false;
                        k.parent.parent.isRed = true;
                        leftRotate(k.parent.parent);
                    }
                }
            } else {
                Node u = k.parent.parent.right;
                if (u != null && u.isRed) {
                    u.isRed = false;
                    k.parent.isRed = false;
                    k.parent.parent.isRed = true;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    if (k.parent != null && k.parent.parent != null) {
                        k.parent.isRed = false;
                        k.parent.parent.isRed = true;
                        rightRotate(k.parent.parent);
                    }
                }
            }
        }
        root.isRed = false;
    }

    private void leftRotate(Node x) {
        if (x == null || x.right == null) return;
        Node y = x.right;
        x.right = y.left;
        if (y.left != NIL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node x) {
        if (x == null || x.left == null) return;
        Node y = x.left;
        x.left = y.right;
        if (y.right != NIL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    public Product search(String id) {
        Node node = searchHelper(root, id);
        return node != NIL ? node.data : null;
    }

    private Node searchHelper(Node node, String id) {
        if (node == NIL || node == null) {
            return NIL;
        }
        if (id.equals(node.data.getId())) {
            return node;
        }
        if (id.compareTo(node.data.getId()) < 0) {
            return searchHelper(node.left, id);
        }
        return searchHelper(node.right, id);
    }
}
