import java.util.*;

public class tree_height {
    static class Node {
        int height = 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int root;
        int[] nodes = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        System.out.println(height(nodes));
    }

    private static int height(int[] nodes) {
        int root;
        Node[] ns = new Node[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == -1) {
                root = i;
                continue;
            }
            ns[nodes[i]].height++;
        }
        int height = 0;
        LinkedList<Node> queue = new LinkedList<>();
        for (Node n : ns) {

        }
    }
}
