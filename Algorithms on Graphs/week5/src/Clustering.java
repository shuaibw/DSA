import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Clustering {
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

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public static double computeDist(Point from, Point to) {
            double dx = from.x - to.x;
            double dy = from.y - to.y;
            return Math.sqrt(dx * dx + dy * dy);
        }
    }

    static class UF {
        private int[] id;
        private int[] sz;
        private int count;

        UF(int n) {
            id = new int[n];
            sz = new int[n];
            Arrays.fill(sz, 1);
            for (int i = 0; i < n; i++) id[i] = i;
            count = n;
        }

        public int count() {
            return count;
        }

        private int root(int i) {
            while (i != id[i]) {
                id[i] = id[id[i]];
                i = id[i];
            }
            return i;
        }

        public boolean connected(int p, int q) {
            return root(p) == root(q);
        }

        public void union(int p, int q) {
            int i = root(p);
            int j = root(q);
            if (i == j) return;
            if (sz[i] < sz[j]) {
                id[i] = j;
                sz[j] += sz[i];
            } else {
                id[j] = i;
                sz[i] += sz[j];
            }
            count--;
        }
    }

    static class Vertex {
        int id;
        double cost;

        Vertex(int id, double cost) {
            this.id = id;
            this.cost = cost;
        }
    }

    private static double clustering(Point[] points, int k) {
        double[][] adj = new double[points.length][points.length];
        double[] cost = new double[points.length];
        Arrays.fill(cost, Integer.MAX_VALUE);
        boolean[] visited = new boolean[points.length];
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dist = Point.computeDist(points[i], points[j]);
                adj[i][j] = dist;
                adj[j][i] = dist;
            }
        }
        PriorityQueue<Vertex> pq = new PriorityQueue<>(Comparator.comparingDouble(o -> o.cost));
        pq.add(new Vertex(0, 0));
        cost[0] = 0;
        int edges = 0;
        ArrayList<Double> list = new ArrayList<>();
        while (!pq.isEmpty() && edges != points.length) {
            Vertex v = pq.poll();
            if (visited[v.id]) continue;
            visited[v.id] = true;
            edges++;
            list.add(v.cost);
            for (int i = 0; i < points.length; i++) {
                if (!visited[i] && cost[i] > adj[v.id][i]) {
                    pq.add(new Vertex(i, adj[v.id][i]));
                    cost[i] = adj[v.id][i];
                }
            }
        }
        list.sort(Double::compare);
        return list.get(points.length - k + 1);
    }

    public static void main(String[] args) throws IOException {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points[i] = new Point(x, y);
        }
        int k = scanner.nextInt();
        System.out.println(clustering(points, k));
    }
}

