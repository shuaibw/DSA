import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class NegativeCycle {
    private static int negativeCycle(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
        long[] dist = new long[adj.length];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;
        for (int i = 0; i < adj.length - 1; i++) {
            if (!relaxEdges(adj, cost, dist)) return 0;
        }
        return relaxEdges(adj, cost, dist) ? 1 : 0;
    }

    private static boolean relaxEdges(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, long[] dist) {
        boolean relaxed = false;
        for (int j = 0; j < adj.length; j++) {
            for (int k = 0; k < adj[j].size(); k++) {
                long newDist = dist[j] + cost[j].get(k);
                if (newDist < dist[adj[j].get(k)]) {
                    relaxed = true;
                    dist[adj[j].get(k)] = newDist;
                }
            }
        }
        return relaxed;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
            cost[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        System.out.println(negativeCycle(adj, cost));
    }
}

