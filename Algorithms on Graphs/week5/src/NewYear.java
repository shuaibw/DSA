import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class NewYear {
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

    private static ArrayList<Integer> distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t) {
        boolean[] visited = new boolean[adj.length];
        long[] dist = new long[adj.length];
        int[] prev = new int[adj.length];
        Arrays.fill(prev, -1);
        prev[s] = -2;
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
                if (visited[n]) continue;
                long newDist = dist[cur.idx] + cost[cur.idx].get(i);
                if (newDist < dist[n]) {
                    dist[n] = newDist;
                    prev[n] = cur.idx;
                    pq.add(new Vertex(n, newDist));
                }
            }
            if (cur.idx == t) break;
        }
        ArrayList<Integer> path = new ArrayList<>();
        path.add(t);
        while (prev[t] != -2) {
            t = prev[t];
            path.add(t);
            if (t == -1) {
                path.clear();
                path.add(-2);
                return path;
            }
        }
        return path;
    }

    public static void main(String[] args) throws IOException {
        FastScanner scanner = new FastScanner();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1000000);
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
            adj[y - 1].add(x - 1);
            cost[x - 1].add(w);
            cost[y - 1].add(w);
        }
        ArrayList<Integer> path = distance(adj, cost, 0, n - 1);
        for (int i = path.size() - 1; i >= 0; i--) {
            out.write((path.get(i) + 1) + " ");
        }
        out.flush();
        out.close();
    }
}
