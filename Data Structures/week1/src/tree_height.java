import java.util.*;

public class tree_height {
    static class Node {
        int data;
        LinkedList<Node> children;

        private Node(int data) {
            this.data = data;
            children = new LinkedList<>();
        }

    }

    private static Node[] nodes;
    private static int[] height;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        nodes = new Node[n];
        height = new int[n];
        for (int i = 0; i < n; i++) nodes[i] = new Node(i);
        Node root = null;
        for (int i = 0; i < n; i++) {
            int parent = scanner.nextInt();
            if (parent == -1) root = nodes[i];
            else nodes[parent].children.add(nodes[i]);
        }
        System.out.println(height(root));
    }

    private static int height(Node root) {
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        height[root.data] = 1;
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            for (Node child : node.children) {
                stack.push(child);
                height[child.data] = height[node.data] + 1;
            }
        }
        return Arrays.stream(height).max().getAsInt();
    }
}
