import java.util.*;
import java.io.*;

public class common_substring {
    public class Answer {
        int i, j, len;

        Answer(int i, int j, int len) {
            this.i = i;
            this.j = j;
            this.len = len;
        }
    }

    int[] sHash1, sHash2, tHash1, tHash2;
    long x1, x2;
    int p1 = 1000000007, p2 = 1000000009;

    public Answer solve(String s, String t) {
        x1 = new Random().nextInt(p1 - 1) + 1;
        x2 = new Random().nextInt(p2 - 1) + 1;
        sHash1 = hashVal(s, p1, x1);
        sHash2 = hashVal(s, p2, x2);
        tHash1 = hashVal(t, p1, x1);
        tHash2 = hashVal(t, p2, x2);
        int l = 0;
        int r = Integer.min(s.length(), t.length());
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (hasCommonSubstr(s, t, mid)) l = mid + 1;
            else r = mid - 1;
        }
        Answer answer = new Answer(0, 0, 0);
        if (l == 0) return answer;
        l -= 1;
        long expo1 = power(x1, l, p1);
        long expo2 = power(x2, l, p2);
        HashMap<Integer, Integer> set1 = new HashMap<>();
        HashMap<Integer, Integer> set2 = new HashMap<>();
        for (int i = 0; i <= (s.length() - l); i++) {
            set1.put(getSubHash(sHash1, i, l, expo1, p1), i);
            set2.put(getSubHash(sHash2, i, l, expo2, p2), i);
        }
        for (int i = 0; i <= (t.length() - l); i++) {
            int hash1 = getSubHash(tHash1, i, l, expo1, p1);
            int hash2 = getSubHash(tHash2, i, l, expo2, p2);
            if (set1.containsKey(hash1) && set2.containsKey(hash2)) {
                answer = new Answer(set1.get(hash1), i, l);
                break;
            }
        }
        return answer;
    }

    private boolean hasCommonSubstr(String s, String t, int l) {
        HashSet<Integer> set1 = new HashSet<>();
        HashSet<Integer> set2 = new HashSet<>();
        long expo1 = power(x1, l, p1);
        long expo2 = power(x2, l, p2);
        for (int i = 0; i <= (s.length() - l); i++) {
            set1.add(getSubHash(sHash1, i, l, expo1, p1));
            set2.add(getSubHash(sHash2, i, l, expo2, p2));
        }
        for (int i = 0; i <= (t.length() - l); i++) {
            int hash1 = getSubHash(tHash1, i, l, expo1, p1);
            int hash2 = getSubHash(tHash2, i, l, expo2, p2);
            if (set1.contains(hash1) && set2.contains(hash2)) return true;
        }
        return false;
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

    public int getSubHash(int[] hash, int beg, int len, long expo, int p) {
        return (int) (((hash[beg + len] - (expo * hash[beg]) % p) + p) % p);
    }

    private int[] hashVal(String s, int p, long x) {
        int[] hashes = new int[s.length() + 1];
        for (int i = 1; i < hashes.length; i++) {
            hashes[i] = (int) ((x * hashes[i - 1] + s.charAt(i - 1)) % p);
        }
        return hashes;
    }

    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out, true);
        in.lines().forEach(line -> {
            StringTokenizer tok = new StringTokenizer(line);
            String s = tok.nextToken();
            String t = tok.nextToken();
            Answer ans = solve(s, t);
            out.format("%d %d %d\n", ans.i, ans.j, ans.len);
        });
        out.close();
    }

    static public void main(String[] args) {
        new common_substring().run();
    }
}
