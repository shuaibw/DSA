import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class ToGo {
    static class FastScanner {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public FastScanner() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public FastScanner(String file_name) throws IOException {
            din = new DataInputStream(
                    new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n') {
                    if (cnt != 0) {
                        break;
                    } else {
                        continue;
                    }
                }
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0,
                    BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }

    static class Vertex {
        int idx;
        long dist;

        Vertex(int idx, long dist) {
            this.idx = idx;
            this.dist = dist;
        }
    }

    private static long distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t) {
        boolean[] visited = new boolean[adj.length];
        long[] dist = new long[adj.length];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[s] = 0;
        PriorityQueue<Vertex> pq = new PriorityQueue<>(Comparator.comparingLong(o -> o.dist));
        pq.add(new Vertex(s, 0));
        while (!pq.isEmpty()) {
            Vertex cur = pq.poll();
            visited[cur.idx] = true;
            if (dist[cur.idx] < cur.dist) continue;
            for (int i = 0; i < adj[cur.idx].size(); i++) {
                int n = adj[cur.idx].get(i);
                if (n == -1 || visited[n]) continue;
                long newDist = dist[cur.idx] + cost[cur.idx].get(i);
                if (newDist < dist[n]) {
                    dist[n] = newDist;
                    pq.add(new Vertex(n, newDist));
                }
            }
            if (cur.idx == t) break;
        }
        return dist[t];
    }

    public static void main(String[] args) throws IOException {
        FastScanner scanner = new FastScanner();
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        int weight = scanner.nextInt();
        int[][] grid = new int[row][col];
        ArrayList<Portal> portals = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int d = scanner.nextInt();
                grid[i][j] = d;
                if (d != 0 && d != -1) portals.add(new Portal(i, j, d));
            }
        }
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[row * col];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[]) new ArrayList[row * col];

        for (int i = 0; i < adj.length; i++) {
            adj[i] = new ArrayList<>();
            cost[i] = new ArrayList<>();
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int id = row * i + j;
                if (grid[i][j] != -1) {
                    connect(i, j, i + 1, j, row, col, weight, adj, cost, grid);
                    connect(i, j, i, j + 1, row, col, weight, adj, cost, grid);
                } else adj[id].add(-1);
            }
        }
        long dist1 = distance(adj, cost, 0, row * col - 1);
        for (int i = 0; i < portals.size(); i++) {
            for (int j = i + 1; j < portals.size(); j++) {
                Portal p1 = portals.get(i);
                Portal p2 = portals.get(j);
                int from = p1.i * col + p1.j;
                int to = p2.i * col + p2.j;
                adj[from].add(to);
                cost[from].add(p1.cost + p2.cost);
            }
        }
        long dist2 = distance(adj, cost, 0, row * col - 1);
        System.out.println(Long.min(dist1, dist2));
    }

    private static void connect(int r1, int c1, int r2, int c2, int row, int col, int weight, ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int[][] grid) {
        if (r2 < 0 || r2 >= row || c2 < 0 || c2 >= col) return;
        if (grid[r2][c2] != -1) {
            int from = r1 * col + c1;
            int to = r2 * col + c2;
            adj[from].add(to);
            cost[from].add(weight);
        }
    }

    static class Portal {
        int i, j, cost;

        Portal(int i, int j, int cost) {
            this.i = i;
            this.j = j;
            this.cost = cost;
        }
    }
}
