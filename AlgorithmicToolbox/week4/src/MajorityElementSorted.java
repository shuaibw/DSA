import java.util.*;
import java.io.*;

public class MajorityElementSorted {
    private static int getMajorityElement(int[] a) {
        Arrays.sort(a);
        int count = 1;
        int threshHold = a.length / 2;
        for (int i = 1; i < a.length; i++) {
            if (a[i] == a[i - 1]) {
                count++;
                if (count > threshHold) return 1;
            } else count = 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        System.out.println(getMajorityElement(a));
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}

