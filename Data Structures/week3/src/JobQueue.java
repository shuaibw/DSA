import java.io.*;
import java.util.Comparator;

public class JobQueue {
    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    static class ThreadSample {
        int id;
        long nextFree;
    }

    private static class MinPQ {

        ThreadSample[] data;
        int size;
        Comparator<ThreadSample> cmp;

        private MinPQ(int n) {
            data = new ThreadSample[n];
            for (int i = 0; i < n; i++) {
                data[i] = new ThreadSample();
                data[i].id = i;
            }
            size = n;
            cmp = (o1, o2) -> {
                if (o1.nextFree != o2.nextFree) return Long.compare(o1.nextFree, o2.nextFree);
                return Integer.compare(o1.id, o2.id);
            };
        }

        private ThreadSample showMin() {
            if (size == 0) throw new RuntimeException("Heap empty");
            return data[0];
        }

        private void changeTopPriority(long p) {
            long old = data[0].nextFree;
            data[0].nextFree = p;
            if (old < p) siftDown(0);
        }

        private void siftDown(int i) {
            while (i < data.length) {
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                int min = i;
                if (left < data.length && cmp.compare(data[left], data[i]) < 0) min = left;
                if (right < data.length && cmp.compare(data[right], data[min]) < 0) min = right;
                if (min == i) break;
                ThreadSample temp = data[i];
                data[i] = data[min];
                data[min] = temp;
                i = min;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs() {
        // TODO: replace this code with a faster algorithm.
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        MinPQ pq = new MinPQ(numWorkers);
        for (int i = 0; i < jobs.length; i++) {
            ThreadSample cur = pq.showMin();
            assignedWorker[i] = cur.id;
            startTime[i] = cur.nextFree;
            pq.changeTopPriority(cur.nextFree + jobs[i]);
        }
    }


    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
    }

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
}
