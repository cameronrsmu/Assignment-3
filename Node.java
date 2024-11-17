public class Node {
    Product data;
    Node parent;
    Node left;
    Node right;
    boolean isRed;

    public Node(Product data) {
        this.data = data;
        this.isRed = true;  // New nodes are always red
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}
