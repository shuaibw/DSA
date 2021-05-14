import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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

    public static void main(String[] args) throws IOException {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        int[] cat = new int[n];
        for (int i = 0; i < n; i++) cat[i] = scanner.nextInt();
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            if (from < to) adj[from - 1].add(to - 1);
            else adj[to - 1].add(from - 1);
        }
        int[] continuousCat = new int[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        int[] level = new int[n];
        continuousCat[0] = cat[0];
        ArrayList<Integer> list = new ArrayList<>();
        while (!queue.isEmpty()) {
            int v = queue.poll();
            if (adj[v].isEmpty()) list.add(continuousCat[v]);
            for (Integer k : adj[v]) {
                queue.add(k);
                level[k] = level[v] + 1;
                continuousCat[k] = continuousCat[v];
                if (continuousCat[k] == -1) continue;
                if (cat[k] == 1) {
                    continuousCat[k]++;
                    if (continuousCat[k] > m) continuousCat[k] = -1;
                } else continuousCat[k] = 0;
            }
        }
        int paths = list.stream().filter(integer -> integer != -1).mapToInt(e -> 1).sum();
        System.out.println(paths);
    }
}
