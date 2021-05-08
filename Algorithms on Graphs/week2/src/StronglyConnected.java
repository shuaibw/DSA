import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class StronglyConnected {
    private static int[] post;
    private static int postCount = 0;
    private static ArrayList<Integer>[] adj;
    private static ArrayList<Integer>[] adjRev;
    private static boolean[] visited;

    private static int numberOfStronglyConnectedComponents() {
        //write your code here
        post = new int[adj.length];
        visited = new boolean[adj.length];
        for (int i = 0; i < adjRev.length; i++) dfsR(i);

        Arrays.fill(visited, false);
        int idx = maxPost();
        int scc = 0;
        while (idx != -1) {
            dfs(idx);
            scc++;
            idx = maxPost();
        }

        return scc;
    }

    private static int maxPost() {
        int idx = -1, max = -1;
        for (int i = 0; i < post.length; i++) {
            if (post[i] > max) {
                max = post[i];
                idx = i;
            }
        }
        return idx;
    }

    private static void dfsR(int v) {
        if (visited[v]) return;
        visited[v] = true;
        for (Integer n : adjRev[v]) {
            if (!visited[n]) dfsR(n);
        }
        post[v] = postCount++;
    }

    private static void dfs(int v) {
        if (visited[v]) return;
        visited[v] = true;
        post[v] = -1;
        for (Integer n : adj[v]) {
            if (!visited[n]) dfs(n);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        adj = (ArrayList<Integer>[]) new ArrayList[n];
        adjRev = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
            adjRev[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adjRev[y - 1].add(x - 1);
        }
        System.out.println(numberOfStronglyConnectedComponents());
    }
}

