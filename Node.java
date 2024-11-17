public class Node {
    Product data;
    Node parent;
    Node left;
    Node right;
    boolean isRed;

    public Node(Product data) {
        this.data = data;
        this.isRed = true;  // new nodes are red
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}
