import java.util.*;
import java.io.*;

public class substring_equality {
    public class Solver {
        private String s;
        private int[] hash1, hash2;
        long x1, x2;
        int p1, p2;

        public Solver(String s) {
            this.s = s;
            p1 = 1000000007;
            p2 = 1000000009;
            Random random = new Random();
            x1 = random.nextInt(p1 - 1) + 1;
            x2 = random.nextInt(p2 - 1) + 1;
            hash1 = hashVal(s, p1, x1);
            hash2 = hashVal(s, p2, x2);
        }

        private int[] hashVal(String s, int prime, long x) {
            int[] hashes = new int[s.length() + 1];
            for (int i = 1; i < hashes.length; i++) {
                hashes[i] = (int) ((x * hashes[i - 1] + s.charAt(i - 1)) % prime);
            }
            return hashes;
        }

        public boolean ask(int a, int b, int l) {
            long expo1 = power(x1, l, p1);
            long expo2 = power(x2, l, p2);
            long h1 = ((hash1[a + l] - (expo1 * hash1[a]) % p1) + p1) % p1;
            long h2 = ((hash1[b + l] - (expo1 * hash1[b]) % p1) + p1) % p1;
            long h3 = ((hash2[a + l] - (expo2 * hash2[a]) % p2) + p2) % p2;
            long h4 = ((hash2[b + l] - (expo2 * hash2[b]) % p2) + p2) % p2;
            return h1 == h2 && h3 == h4;

        }
    }

    static long power(long x, long y, int p) {
        long res = 1; // Initialize result
        x = x % p; // Update x if it is more than or
        // equal to p
        if (x == 0)
            return 0; // In case x is divisible by p;
        while (y > 0) {
            // If y is odd, multiply x with result
            if ((y & 1) != 0)
                res = (res * x) % p;
            // y must be even now
            y = y >> 1; // y = y/2
            x = (x * x) % p;
        }
        return res;
    }

    public void run() throws IOException {
        FastScanner in = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        String s = in.readLine();
        int q = in.nextInt();
        Solver solver = new Solver(s);
        for (int i = 0; i < q; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int l = in.nextInt();
            out.println(solver.ask(a, b, l) ? "Yes" : "No");
        }
        out.close();
    }

    static public void main(String[] args) throws IOException {
        new substring_equality().run();
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
            byte[] buf = new byte[500001]; // line length
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
