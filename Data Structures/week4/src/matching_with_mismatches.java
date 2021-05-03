import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class matching_with_mismatches {
    private int prime = 1000000007, threshold;
    private long x;
    private long[] expo;
    private int[] tHash, pHash;
    private String t, p;

    public List<Integer> solve(int k, String t, String p) {
        this.t = t;
        this.p = p;
        x = new Random().nextInt(prime - 1) + 1;
        expo = new long[p.length() + 1];
        for (int i = 0; i <= p.length(); i++) expo[i] = power(x, i, prime);
        threshold = k;
        ArrayList<Integer> pos = new ArrayList<>();
        tHash = preComputeHash(t);
        pHash = preComputeHash(p);
        for (int i = 0; i <= t.length() - p.length(); i++) {
            if (countMismatch(i, 0, p.length() - 1) <= k) pos.add(i);
        }
        return pos;
    }

    public int countMismatch(int i, int l, int r) {
        int mismatches = 0;
        if (l == r && t.charAt(i + l) != p.charAt(l)) return 1;
        if (l >= r) return 0;
        int len = r - l + 1;
        if (getSubHash(tHash, i + l, len) == getSubHash(pHash, l, len)) return 0;
        int mid = l + (r - l) / 2;
        if (t.charAt(mid + i) != p.charAt(mid)) mismatches++;
        mismatches = mismatches + countMismatch(i, mid + 1, r);
        mismatches = mismatches + countMismatch(i, l, mid - 1);
        return mismatches;
    }

    public int getSubHash(int[] hash, int beg, int len) {
        return (int) (((hash[beg + len] - (expo[len] * hash[beg]) % prime) + prime) % prime);
    }

    public int[] preComputeHash(String s) {
        int[] hash = new int[s.length() + 1];
        for (int i = 1; i < hash.length; i++) hash[i] = (int) ((x * hash[i - 1] + s.charAt(i - 1)) % prime);
        return hash;
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

    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out, true);
        in.lines().forEach(line -> {
            StringTokenizer tok = new StringTokenizer(line);
            int k = Integer.parseInt(tok.nextToken());
            String s = tok.nextToken();
            String t = tok.nextToken();
            List<Integer> ans = solve(k, s, t);
            out.format("%d ", ans.size());
            out.println(ans.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(" "))
            );
        });
        out.close();
    }

    static public void main(String[] args) {
        new matching_with_mismatches().run();
    }
}
