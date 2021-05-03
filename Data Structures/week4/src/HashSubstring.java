import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;
    private int prime = 1000000007;
    private int x;
    private int hashLen;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        new HashSubstring().compute();
        out.close();
    }

    private void compute() throws IOException {
        printOccurrences(getOccurrences(readInput()));
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * x + s.charAt(i)) % prime;
        return (int) hash;
    }

    private Data readInput() throws IOException {
        String pattern = in.readLine();
        String text = in.readLine();
        return new Data(pattern, text);
    }

    private void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    private List<Integer> getOccurrences(Data input) {
        Random random = new Random();
        x = random.nextInt(prime) + 1;
        String p = input.pattern, t = input.text;
        int m = p.length(), n = t.length();
        hashLen = n - m + 1;
        List<Integer> occurrences = new ArrayList<>();
        int pHash = hashFunc(p);
        int[] preComputedHashes = preComputeHash(t, m);
        for (int i = 0; i < hashLen; i++) {
            if (pHash != preComputedHashes[i]) continue;
            if (t.substring(i, i + m).equals(p)) occurrences.add(i);
        }
        return occurrences;
    }

    private int[] preComputeHash(String t, int pLen) {
        int[] hash = new int[hashLen];
        int lastHash = hashFunc(t.substring(t.length() - pLen));
        hash[hashLen - 1] = lastHash;
        long expo = 1;
        for (int i = 0; i < pLen; i++) {
            expo = (expo * x) % prime;
        }
        for (int i = hashLen - 2; i >= 0; i--) {
            long pre = ((long) x * hash[i + 1]) % prime;
            pre = (pre + t.charAt(i)) % prime;
            pre = (pre - expo * t.charAt(i + pLen)) % prime;
            if (pre < 0) pre = (pre + prime) % prime;
            hash[i] = (int) pre;
        }
        return hash;
    }

    static class Data {
        String pattern;
        String text;

        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
        }
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
            byte[] buf = new byte[600000]; // line length
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

