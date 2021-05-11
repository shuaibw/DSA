import java.util.ArrayList;
import java.util.Scanner;

public class ShortestPaths {
    private static final long POS_INF = 100000000000000L;
    private static final long NEG_INF = -100000000000000L;

    private static void shortestPaths(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, long[] distance) {
        distance[s] = 0;

        for (int i = 0; i < adj.length - 1; i++) {
            if (!relaxEdges(adj, cost, distance, false)) return;
        }
        for (int i = 0; i < adj.length - 1; i++) {
            if (!relaxEdges(adj, cost, distance, true)) return;
        }
    }

    private static boolean relaxEdges(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, long[] dist, boolean checkCycle) {
        boolean relaxed = false;
        for (int j = 0; j < adj.length; j++) {
            if (dist[j] == POS_INF) continue;
            for (int k = 0; k < adj[j].size(); k++) {
                if (dist[adj[j].get(k)] == NEG_INF) continue;
                long newDist = dist[j] + cost[j].get(k);
                if (newDist < dist[adj[j].get(k)]) {
                    relaxed = true;
                    if (!checkCycle) dist[adj[j].get(k)] = newDist;
                    else dist[adj[j].get(k)] = NEG_INF;
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
        int s = scanner.nextInt() - 1;
        long[] distance = new long[n];
        for (int i = 0; i < n; i++) {
            distance[i] = POS_INF;
        }
        shortestPaths(adj, cost, s, distance);
        for (int i = 0; i < n; i++) {
            long dist = distance[i];
            String res = null;
            if (dist == POS_INF) res = "*";
            else if (dist == NEG_INF) res = "-";
            System.out.println(res == null ? dist : res);
        }
    }

}

